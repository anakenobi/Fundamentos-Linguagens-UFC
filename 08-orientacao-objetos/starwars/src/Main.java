import entities.Personagem;
import entities.Sith;
import entities.Jedi;
import entities.Droide;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Personagem um = new Sith("Darth Vader", "Tatooine", "Lorde Sombrio dos Sith");
        Personagem dois = new Droide("C3PO", "Tatooine", "droide de protocolo");
        Personagem tres = new Jedi ("Obi Wan Kenobi", "Stewjon", "azul");

        um.apresentar();
        dois.apresentar();
        tres.apresentar();




    }
}