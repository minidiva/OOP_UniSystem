package util;
import java.util.*;

public class Printer { 
	public void println(String text) { 
		System.out.println(text); 
	}

	public void println(Object object) {
		System.out.println(object);
	}

	public void printList(List<?> items) { 
		if (items.isEmpty()) { System.out.println("Список пуст."); return; 
		} 
		items.forEach(System.out::println); 
	}
}
