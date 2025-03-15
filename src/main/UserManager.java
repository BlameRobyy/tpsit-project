import java.io.*;
import java.util.HashMap;

public class UserManager {
    private static final String FILE_NAME = "users_data.ser"; // Nome del file per la serializzazione
    private HashMap<String, User> usersMap;

    public UserManager() {
        this.usersMap = loadUsers();
    }

    // Aggiungi un utente alla mappa
    public void addUser(User user) {
        usersMap.put(user.getPersonalCodeUser(), user);
    }

    // Carica gli utenti dal file di salvataggio
    private HashMap<String, User> loadUsers() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (HashMap<String, User>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new HashMap<>(); // Se non esiste, ritorna una mappa vuota
    }

    // Salva gli utenti nel file
    public void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(usersMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Ottieni un utente dalla mappa
    public User getUser(String personalCode) {
        return usersMap.get(personalCode);
    }

    // Verifica se l'utente esiste
    public boolean userExists(String personalCode) {
        return usersMap.containsKey(personalCode);
    }

    // Restituisce tutti gli utenti
    public HashMap<String, User> getUsersMap() {
        return usersMap;
    }
}
