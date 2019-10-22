/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arvorebalanceada;
public class No {
    
    private int Info;
    private No pai;
    private No dir;
    private No esq;
    private int balanceamento;
    
    public No(int i){
        dir = null;
        esq = null;
        pai = null;
        Info = i;
    }

    public int getBalanceamento() {
        return balanceamento;
    }

    public void setBalanceamento(int balanceamento) {
        this.balanceamento = balanceamento;
    }

    public No getPai() {
        return pai;
    }

    public void setPai(No pai) {
        this.pai = pai;
    }
    
    public int getInfo() {
        return Info;
    }

    public void setInfo(int Info) {
        this.Info = Info;
    }

    public No getDir() {
        return dir;
    }

    public void setDir(No dir) {
        this.dir = dir;
    }

    public No getEsq() {
        return esq;
    }

    public void setEsq(No esq) {
        this.esq = esq;
    }
    
}
