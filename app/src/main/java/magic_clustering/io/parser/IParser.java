package magic_clustering.io.parser;

public interface IParser<T> {
	
	T parse(String path);
	
}
