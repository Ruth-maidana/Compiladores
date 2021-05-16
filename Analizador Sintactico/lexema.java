/**
 *
 * @author Ruth
 */
public class lexema {
    //variable que realiza el analisis lexico
    lexico Lexico= new lexico();
    char token[];
    //guarda el lexema de cada string
    String tokens; 
    String input; 
    String pl=""; //entrada
    int j=0;
    int i;
    int band2=0;
    int band1=0;
  
    public lexema(String input,int i) {
        this.input = input;
        this.i= 0;
        token = new char[input.length()];//array para guardar tokens 
        tokens="";     
    }
 
    public void getToken(){
        while( i < input.length()){ 
            if(i>1){  
                if( (Lexico.ControlLimitador(input.charAt(i-1))==0)  
                      &&  band1==1 
                        ){ 
                    Lexico.limitador(input.charAt(i-1));
                    tokens= Lexico.getTokens();
                    pl= Lexico.getPal();
                    band1=0;
                    break; 
                }           
            }        
            
            //Verifica y guarda los tokens si son correctos 
            if(Lexico.limitador(input.charAt(i))==0){
                if(band2==1){
                    Lexico.detectarExpReg(token);   
                    token= null;
                    token = new char[input.length()];
                    j=0;
                    band1=1;
                }
                band2=0;
                i++; 
                break;
            } else {
                //Se guardan las palabras que no contienen limitadores
                band2=1;
                token[j]= input.charAt(i);
                j++;
                i++;
                //Verifica si un string esta completo o contiene algun limitador
                if(token[0]== '\"'){
                    while( Lexico.ControlLimitador(input.charAt(i))==0 ){
                        if(token[j-1]=='\"' ){   break;}
                            token[j]= input.charAt(i); 
                            i++;
                            j++;
                    }
                } 
            }
        }
        
        if(band2==1){
            Lexico.detectarExpReg(token);   
            token= null;
            token = new char[input.length()];
            j=0;
                      
        }
        tokens= Lexico.getTokens();
        pl= Lexico.getPal();
    }
   //Recuperan los tokens utilizados
    public String getTokens() {
        return tokens;
    }
    
    public String getPal() {
        return pl;
    }
    
    public int getTam() {
        return i;
    }
    
}
