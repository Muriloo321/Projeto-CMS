package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import models.entities.User;
import services.UserService;

public class Menu {
    protected Scanner scan = new Scanner(System.in);
    protected UserService userService = new UserService();
    private ContentView contentView;
    private UserView userView;
    User actualUser;

    public Menu() {
        this.contentView = new ContentView(this);
        this.userView = new UserView(this);
    }

    public void mainMenu() {
        int choice;

        System.out.println("\n==================================================");
        System.out.println("                   CADASTRE-SE");
        System.out.println("==================================================");

        User newRegisteredUser = userView.instantiateUser();
        userService.register(newRegisteredUser);

        System.out.println("\n==================================================");
        System.out.println("             BOAS VINDAS, " + newRegisteredUser.getName() + "!");
        System.out.println("==================================================");

        while (true) {
            System.out.println("\n==================================================");
            System.out.println("                MENU INICIAL");
            System.out.println("==================================================");
            System.out.println("1- Login");
            System.out.println("2- Listar Conteúdos");
            System.out.println("3- Sair");
            System.out.println("==================================================");
            System.out.print("Escolha uma opção: ");

            try {
                choice = scan.nextInt();
                scan.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("\n==================================================");
                        System.out.println("                      LOGIN");
                        System.out.println("==================================================");

                        User loggedInUser = userView.instantiateUser();

                        if (userService.login(loggedInUser)) {
                            System.out.println("\n==================================================");
                            System.out.println("             BOAS VINDAS, " + loggedInUser.getName() + "!");
                            System.out.println("==================================================");
                            actualUser = userService.findByUser(loggedInUser);
                            inicializar();
                        } else {
                            System.out.println("Usuário ou senha incorretos. Tente novamente.");
                        }
                        break;

                    case 2:
                        System.out.println("\n==================================================");
                        System.out.println("                LISTA DE CONTEÚDOS");
                        System.out.println("==================================================");
                        contentView.listContentsMenu();
                        System.out.println("==================================================");
                        break;
                    case 3:
                        System.out.println("Programa Encerrado. Até logo!");
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                scan.nextLine();
            }
        }
    }

    private void inicializar() {
        int optChoice;

        while (true) {
            System.out.println("\n==================================================");
            System.out.println("                     MENU PRINCIPAL");
            System.out.println("==================================================");
            System.out.println("1- Criar Conteúdos");
            System.out.println("2- Listar Conteúdos");
            System.out.println("3- Atualizar Conteúdos");
            System.out.println("4- Excluir Conteúdos");
            System.out.println("5- Criar Usuário");
            System.out.println("6- Listar Usuários");
            System.out.println("7- Alterar Usuário");
            System.out.println("8- Excluir Usuário");
            System.out.println("9- Mudar Senha");
            System.out.println("10- Logout");
            System.out.println("==================================================");
            System.out.print("Escolha uma opção: ");

            try {
                optChoice = scan.nextInt();
                scan.nextLine();

                switch (optChoice) {
                    case 1:
                        System.out.println("\n==================================================");
                        System.out.println("              Criar Novo Conteúdo");
                        System.out.println("==================================================");
                        contentView.insertContentMenu(actualUser);
                        break;
                    case 2:
                        System.out.println("\n==================================================");
                        System.out.println("               Listagem de Conteúdos");
                        System.out.println("==================================================");
                        contentView.listContentsMenu();
                        break;
                    case 3:
                        System.out.println("\n==================================================");
                        System.out.println("            Atualizar Informações do Conteúdo");
                        System.out.println("==================================================");
                        contentView.editContentsMenu();
                        break;
                    case 4:
                        System.out.println("\n==================================================");
                        System.out.println("              Excluir Conteúdo");
                        System.out.println("==================================================");
                        contentView.deleteContentsMenu();
                        break;
                    case 5:
                        System.out.println("\n==================================================");
                        System.out.println("              Criar Novo Usuário");
                        System.out.println("==================================================");
                        userView.registerUserMenu();
                        break;
                    case 6:
                        System.out.println("\n==================================================");
                        System.out.println("               Listagem de Usuários");
                        System.out.println("==================================================");
                        userView.listUsersMenu();
                        break;
                    case 7:
                        System.out.println("\n==================================================");
                        System.out.println("            Atualizar Informações do Conteúdo");
                        System.out.println("==================================================");
                        userView.updateUserMenu();
                        break;
                    case 8:
                        System.out.println("\n==================================================");
                        System.out.println("                  Excluir Usuário");
                        System.out.println("==================================================");
                        
                        List<User> users = userView.listUsersMenu();
                        if (users == null || users.size() <= 1) {
                            System.out.println("Impossível deletar, pois há apenas um usuário!");
                            break;
                        }
                        
                        userView.deleteUserMenu();
                        break;
                    case 9:
                        System.out.println("==================================================");
                        System.out.println("                 Alterar Senha.");
                        System.out.println("==================================================");
                        userView.changePasswordMenu(actualUser);
                        break;
                    case 10:
                        System.out.println("==================================================");
                        System.out.println("                 Logout efetuado.");
                        System.out.println("==================================================");
                        return;
                    default:
                        System.out.println("==================================================");
                        System.out.println("              Opção inválida. Tente novamente.");
                        System.out.println("==================================================");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Por favor, insira um número.");
                scan.nextLine();
            }
        }
    }
}
