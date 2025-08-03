package entities;

public class Personagem {
    protected String nome;
    protected String planeta;

    public Personagem(String nome, String planeta)
    {
        this.nome = nome;
        this.planeta = planeta;
    }
    public void apresentar()
    {
        System.out.println("Sou " + nome + " do Planeta " + planeta);
    }


}