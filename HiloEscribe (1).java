public class HiloEscribe extends Thread{
    private String info[];
    private int ini;
    private int tmp;
    private Posicion p;
    private boolean terminarHilo = false;
    private boolean pausarHilo = false;
    
    
    public HiloEscribe(Posicion x){
        this.p=x;
    }
    public void setTmp(int tmp) {
        this.tmp = tmp;
    }
    public void setTerminar(boolean t) {
        this.terminarHilo = t;
    }
    public synchronized void setPausar(boolean p) {
        this.pausarHilo = p;
        if (!p) {
            notify(); 
        }
    }
    @Override
    public void run(){
        for(int i=ini; i < ini + 5 && !terminarHilo; i++)
        {
            try {
                synchronized (this) {
                    while (pausarHilo) { 
                        wait();
                    }
                }
            } catch (InterruptedException e) {
                if(terminarHilo) {
                    return;
                }
            }
           try{
                synchronized(p){
                    info[p.getP()]=getName()+i+"-"+p.getP();
                    p.incrementa();
                }
                sleep(tmp);
           }
           catch(ArrayIndexOutOfBoundsException e){
                System.out.println("NO HAY ESPACIO");
                break;
            }
           catch(InterruptedException e){
                System.out.println("INTERRUPCION");
                if(terminarHilo) {
                    break;
                }
            }
        }
    }
    public void setIni(int x){
        ini=x;
    }
    public void setInfo(String x[]){
        info=x;
    }
}
 