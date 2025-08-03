package entities;

public class Jedi extends Personagem {
    private String sabreDeLuz;

    public Jedi (String nome, String planeta, String sabreDeLuz)
    {
        super(nome, planeta);
        this.sabreDeLuz = sabreDeLuz;
    }

    @Override
    public void apresentar() {
        super.apresentar();
        System.out.println("Sou um Jedi. Meu sabre de luz é " + sabreDeLuz + ".");
    }
}