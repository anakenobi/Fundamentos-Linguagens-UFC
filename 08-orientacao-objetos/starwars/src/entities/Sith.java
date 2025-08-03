package entities;

public class Sith extends Personagem {
    private String titulo;

    public Sith(String nome, String planeta, String titulo)
    {
        super(nome, planeta);
        this.titulo = titulo;
    }

    @Override
    public void apresentar() {
        super.apresentar();
        System.out.println("Sou um Sith. Me chamam de " + titulo + ".");
    }
}
