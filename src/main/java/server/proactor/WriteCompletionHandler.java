package server.proactor;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class WriteCompletionHandler implements CompletionHandler<Integer, SessionState> {
    private AsynchronousSocketChannel socketChannel;
	private boolean writing;
    private static Map<AsynchronousSocketChannel, WriteCompletionHandler> handlers = new HashMap<>();
    private Queue<DataToWrite> writeQueue = new LinkedList<>();

    // Create a new handler or return the previously created one based on the socketChannel.    
    public static WriteCompletionHandler getHandler(AsynchronousSocketChannel socketChannel){
    	if(handlers.containsKey(socketChannel)){
    		return handlers.get(socketChannel);
    	}else{
    		WriteCompletionHandler handler = new WriteCompletionHandler(socketChannel);
    		handlers.put(socketChannel, handler);
    		return handler;
    	}
    }
    
    private WriteCompletionHandler(AsynchronousSocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    /**
     *  if there is more data to write initiate that.  
     */
    @Override
    public void completed(Integer bytesWritten, SessionState attachment) {
    	writeNext();
    }

    /**
     *  if there is more data to write initiate that.  
     */
	@Override
    public void failed(Throwable exc, SessionState attachment) {
    	writeNext();
    }
    
    private boolean hasDataToWrite() {
		return !writeQueue.isEmpty();
	}

	/**
	 * Add new data to write queue. Then initiate the next write if a write operation is not in progress.
	 * @param outputBuffer
	 * @param sessionState
	 */
    public void write(ByteBuffer outputBuffer, SessionState sessionState) {
		DataToWrite dataToWrite = new DataToWrite(outputBuffer, sessionState);
		writeQueue.add(dataToWrite);
		if(!writing){
			writeNext();
		}
	}
	
    /**
     * Get the next data from queue and write it to the socket.
     */
	private synchronized void writeNext(){	
		if(hasDataToWrite()){
			this.writing = true;
			DataToWrite dataToWrite = writeQueue.poll();
			socketChannel.write(dataToWrite.outputBuffer, dataToWrite.sessionState, this);		
		}else{
			this.writing = false;
		}		
	}
	
	/**
	 * Data structure for keeping the data to write.
	 */
	class DataToWrite{
		ByteBuffer outputBuffer; 
		SessionState sessionState;
		public DataToWrite(ByteBuffer outputBuffer, SessionState sessionState){
			this.outputBuffer = outputBuffer;
			this.sessionState = sessionState;
		}
	}
}