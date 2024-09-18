package view;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import models.entities.User;
import services.UserService;

public class UserView {
	private Menu menu;
	private UserService userService;

	public UserView(Menu menu) {
		this.menu = menu;
		this.userService = new UserService();
	}

	public void registerUserMenu() {
		System.out.print("Nome de usuário: ");
		String name = menu.scan.nextLine();

		System.out.print("Senha: ");
		String password = menu.scan.nextLine();

		User user = new User(null, name, password);
		boolean success = userService.register(user);

		if (success) {
			System.out.println("Usuário registrado com sucesso!");
		} else {
			System.out.println("Nome de usuário já existe.");
		}
	}

	public void findUserByNameMenu() {
		System.out.print("Nome de usuário: ");
		String name = menu.scan.nextLine();

		User user = userService.findByName(name);
		if (user != null) {
			System.out.println("Usuário encontrado:");
			System.out.println(user);
		} else {
			System.out.println("Usuário não encontrado.");
		}
	}

	public void updateUserMenu() {
		System.out.print("Digite o ID do usuário a ser atualizado: ");
		try {
			int userId = menu.scan.nextInt();
			menu.scan.nextLine();

			User user = userService.findById(userId);
			if (user == null) {
				System.out.println("Usuário não encontrado.");
				return;
			}

			System.out.println("Digite as mudanças (DEIXE EM BRANCO CASO QUEIRA MANTER O MESMO VALOR):");
			System.out.print("Nome: ");
			String nameNew = menu.scan.nextLine();
			if (!nameNew.isBlank()) {
				user.setName(nameNew);
			}

			System.out.print("Senha: ");
			String passwordNew = menu.scan.nextLine();
			if (!passwordNew.isBlank()) {
				user.setPassword(passwordNew);
			}

			String result = userService.update(user);
			System.out.println(result);

			if (userId == menu.actualUser.getId()) {
				menu.actualUser = userService.findById(userId);
			}
		} catch (InputMismatchException e) {
			System.out.println("Entrada inválida. Por favor, insira um número.");
			menu.scan.nextLine();
		}
	}

	public void deleteUserMenu() {
		System.out.print("Digite o ID do usuário a ser deletado: ");
		try {
			int userId = menu.scan.nextInt();

			String result = userService.deleteById(userId);
			System.out.println(result);
		} catch (InputMismatchException e) {
			System.out.println("Entrada inválida. Por favor, insira um número.");
			menu.scan.nextLine();
		}
	}

	public List<User> listUsersMenu() {
		List<User> list = userService.findAll();
		if (list.isEmpty()) {
			System.out.println("Lista de usuários vazia.");
		} else {
			list.forEach(System.out::println);
			return list;
		}
		return null;
	}

	public void changePasswordMenu(User actualUser) {
		try {
			System.out.print("Digite sua senha atual: ");
			String currentPassword = menu.scan.nextLine();

			if (!userService.login(new User(actualUser.getId(), actualUser.getName(), currentPassword))) {
				System.out.println("Senha atual incorreta. Não é possível alterar a senha.");
				return;
			}

			System.out.print("Digite sua nova senha: ");
			String newPassword = menu.scan.nextLine();

			System.out.print("Confirme sua nova senha: ");
			String confirmPassword = menu.scan.nextLine();

			if (!newPassword.equals(confirmPassword)) {
				System.out.println("As senhas não coincidem. Tente novamente.");
				return;
			}

			actualUser.setPassword(newPassword);
			String result = userService.update(actualUser);
			System.out.println(result);
		} catch (InputMismatchException e) {
			System.out.println("Entrada inválida. Por favor, insira um valor correto.");
			menu.scan.nextLine();
		}
	}

	public User instantiateUser() {
		Scanner scan = new Scanner(System.in);
		String user;
		String password;

		while (true) {
			System.out.print("Usuário: ");
			user = scan.nextLine();

			System.out.print("Senha: ");
			password = scan.nextLine();

			if (user.isBlank()) {
				System.out.println("Usuário nulo! Por favor, insira um nome de usuário.");
			} else if (password.isBlank()) {
				System.out.println("Senha nula! Por favor, insira uma senha.");
			} else {
				return new User(null, user, password);
			}
		}
	}
}
