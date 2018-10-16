package banco.modelo;

public class Conta {

    private int id;
    private int agencia;
    private int numero;
    private Cliente cliente;
    private double saldo;

    public Conta() {
    }

    public Conta(int id, int agencia, int numero, double saldo, Cliente cliente) {
        this.id = id;
        this.agencia = agencia;
        this.numero = numero;
        this.cliente = cliente;
        this.saldo = saldo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAgencia() {
        return agencia;
    }

    public void setAgencia(int agencia) {
        this.agencia = agencia;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Agência: " + getAgencia()
                + "\nNúmero: " + getNumero()
                + "\nCliente: " + getCliente()
                + String.format("\nSaldo: R$ %.2f",
                        getSaldo());
    }

}
