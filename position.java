package maze;
public class position {
 int i,j; 
 
 public position(int i, int j){
 this.i=i;
 this.j=j;
 };
 public int i() { 
     return i;
 } 
 public int j() { 
     return j;
 } 
  
 public position north(){
 return new position(i-1,j);
 }
 
 public position south(){
 return new position(i+1 , j);
 }
 
 public position east(){
 return new position(i,j+1);
 }
 
 public position west(){
 return new position(i,j-1);
 }
}