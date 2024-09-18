package view;

import java.util.InputMismatchException;
import java.util.List;

import models.entities.Content;
import models.entities.User;
import services.ContentService;

public class ContentView {
    private Menu menu;
    ContentService contents = new ContentService();

    public ContentView(Menu menu) {
        this.menu = menu;
    }

    public void insertContentMenu(User user) {
        System.out.print("Título do conteúdo: ");
        String title = menu.scan.nextLine();

        System.out.print("Texto do conteúdo: ");
        String text = menu.scan.nextLine();

        String result = contents.insertContent(title, text, user);
        System.out.println(result);
    }

    public void listContentsMenu() {
        List<Content> list = contents.listContents();
        if (list.isEmpty()) {
            System.out.println("Lista de conteúdos vazia.");
        } else {
            list.forEach(System.out::println);
        }
    }

    public void editContentsMenu() {
        try {
            System.out.print("Digite o ID do conteúdo a ser editado: ");
            int contentId = menu.scan.nextInt();
            menu.scan.nextLine();

            Content contentToEdit = contents.contentDao.findById(contentId);
            if (contentToEdit == null) {
                System.out.println("Conteúdo inexistente.");
                return;
            }

            System.out.println("Digite as mudanças (DEIXE EM BRANCO CASO QUEIRA MANTER O MESMO VALOR):");
            System.out.print("Título: ");
            String titleNew = menu.scan.nextLine();

            System.out.print("Texto: ");
            String textNew = menu.scan.nextLine();

            String result = contents.editContents(contentId, titleNew, textNew);
            System.out.println(result);
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número.");
            menu.scan.nextLine();
        }
    }

    public void deleteContentsMenu() {
        try {
            System.out.print("Digite o ID do conteúdo a ser deletado: ");
            int contentId = menu.scan.nextInt();

            String result = contents.deleteContents(contentId);
            System.out.println(result);
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida. Por favor, insira um número.");
            menu.scan.nextLine();
        }
    }
}
