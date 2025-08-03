package entities;

public class Droide extends Personagem{
    private String funcao;

    public Droide(String nome, String planeta, String funcao)
    {
        super(nome, planeta);
        this.funcao = funcao;
    }

    @Override
    public void apresentar() {
        super.apresentar();
        System.out.println("Sou um droide. Minha função é: " + funcao + ".");
    }
}