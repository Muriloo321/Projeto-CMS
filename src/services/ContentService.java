package services;

import java.util.List;

import models.dao.ContentDao;
import models.dao.DaoFactory;
import models.entities.Content;
import models.entities.User;

public class ContentService {

	public ContentDao contentDao = DaoFactory.createContentDao();

	public String insertContent(String title, String text, User author) {
		Content content = new Content(null, title, text, author);
		contentDao.insert(content);
		return "Livro inserido com sucesso!";
	}

	public List<Content> listContents() {
		return contentDao.findAll();
	}

	public String editContents(int contentId, String titleNew, String textNew) {
		Content contentToEdit = contentDao.findById(contentId);
		
		if (contentToEdit == null) {
			return "Livro inexistente.";
		}

		if (!titleNew.isBlank()) {
			contentToEdit.setTitle(titleNew);
		}
		
		if (!textNew.isBlank()) {
			contentToEdit.setText(textNew);
		}

		contentDao.update(contentToEdit);
		return "Informações do conteúdo atualizadas com sucesso!";
	}

	public String deleteContents(int contentId) {
		Boolean removed = contentDao.deleteById(contentId);
		if (removed) {
			return "Conteúdo excluído com sucesso!";
		} else {
			return "Conteúdo não encontrado.";
		}
	}
}
