package models.entities;

import java.io.Serializable;
import java.util.Objects;

public class Content implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//OBS: Content é "conteúdo" em inglês.
	
	private Integer id;
	private String title;
	private String text;
	private User author;
	
	
	public Content() {
	}
	
	public Content(Integer id, String title, String text, User author) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.author = author;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Content other = (Content) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
	    return String.format(
	        "------------------------------------\n" +
    		" ID:                "+id+"\n"+
	        " Título:            "+title+"\n" +
	        " Texto:             "+text+"\n" +
	        " Autor:             "+author.getName()+"\n" +
	        "------------------------------------\n"
	    );
	}	
}
