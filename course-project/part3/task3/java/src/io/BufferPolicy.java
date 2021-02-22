package src.io;

public interface BufferPolicy<O> {
    O enforce(byte b);
}
