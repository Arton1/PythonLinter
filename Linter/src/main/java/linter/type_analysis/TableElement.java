package linter.type_analysis;

import java.util.List;

public interface TableElement<T> {
    public T getElement(List<String> identifier);
	public void addElement(List<String> subList, T element);
}