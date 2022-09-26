package maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Stack;
import javafx.util.Pair;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Maze extends JFrame {
 final static int b = 1;
 final static int s = 0; 
 final static int i = 2; 
 final static int d = 8; 
 final static int p = 9;
 final static int start_i = 1, start_j = 1;
 final static int end_i = 2, end_j = 9;
 int[][] maze = new int[][]{
 {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
 {1, 2, 0, 0, 0, 0, 0, 0, 0, 1},
 {1, 0, 0, 0, 1, 0, 1, 1, 0, 8},
 {1, 0, 0, 1, 1, 0, 1, 0, 0, 1},
 {1, 1, 0, 0, 0, 1, 0, 1, 0, 1},
 {1, 1, 1, 1, 0, 0, 1, 0, 0, 1},
 {1, 0, 1, 1, 0, 1, 0, 0, 0, 1},
 {1, 0, 0, 1, 0, 0, 1, 0, 0, 1},
 {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
 {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
 };
 
 int[][] arr;
 
 JButton DFS;
 JButton BFS;
 JButton clear;
 JButton exit;
 JButton genrandom;

 JLabel elapsedDfs;
 JTextField textDfs;
 JLabel elapsedBFS;
 JTextField textBFS;
 
 boolean repaint = false;
 
 long startTime;
 long stopTime;
 long duration;
 double dfsTime;
 double bfsTime;

 int[][] savedmaze = clone();
 
 public Maze() {
 setTitle("Maze"); 
 setSize(960, 530);
 setLocationRelativeTo(null);
 setResizable(false);
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setLayout(null); 

 elapsedDfs = new JLabel("Time :");
 elapsedBFS = new JLabel("Time :");
 textDfs = new JTextField();
 textBFS = new JTextField();

 DFS = new JButton("DFS");
 BFS = new JButton("BFS");
 clear = new JButton("Clear");
 exit = new JButton("Exit");
 genrandom = new JButton("Generate Random Maze");

 add(DFS);
 add(BFS); 
 add(clear);
 add(elapsedDfs);
 add(textDfs);
 add(elapsedBFS);
 add(textBFS);
 add(exit);
 add(genrandom);

 setVisible(true);

 DFS.setBounds(500, 50, 100, 40);
 BFS.setBounds(630, 50, 100, 40);
 clear.setBounds(500, 230, 170, 40);
 exit.setBounds(500, 280, 170, 40);
 elapsedDfs.setBounds(500, 100, 100, 40);
 genrandom.setBounds(500, 180, 170, 40);
 elapsedBFS.setBounds(630, 100, 100, 40);
 textDfs.setBounds(500, 130, 100, 25);
 textBFS.setBounds(630, 130, 100, 25);


 genrandom.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 int x[][] = GenerateArray(); 
 repaint = true;
 restore(x); 
 repaint(); 
 }
 });


 exit.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 System.exit(0); 
 }
 });


 clear.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 if (arr == null) {
 repaint = true;
 restore(savedmaze); 
 repaint();
 } else {
 repaint = true;
 restore(arr); 
 repaint(); 
 }
 textBFS.setText(""); 
 textDfs.setText("");

 }
 });

 DFS.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 if (arr == null) {
 restore(savedmaze);
 repaint = false;
 stack();
repaint();
 } else {
 restore(arr);
 repaint = false;
 stack();
repaint();
 }
 }
 });


 BFS.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 if (arr == null) {
 restore(savedmaze);
 repaint = false;
 queue();
repaint();
 } else {
 restore(arr);
 repaint = false;
 queue();
 repaint();
 }
 }
 });
 
 
  }
 
 
 public int Size() {
 return maze.length;
 }

 public void Print() {
 for (int i = 0; i < Size(); i++) {
 for (int J = 0; J < Size(); J++) {
 System.out.print(maze[i][J]);
 System.out.print(' ');
 }
 System.out.println();
 }
 }
 
 public boolean isInMaze(int i, int j) {
 if (i >= 0 && i < Size() && j >= 0 && j < Size()) {
 return true;
 } else {
 return false;
 }
 }
 public boolean isInMaze(position pos) {
 return isInMaze(pos.i(), pos.j());
 }

 public int mark(int i, int j, int value) {
 assert (isInMaze(i, j)); 
 int temp = maze[i][j];
 maze[i][j] = value;
 return temp;
 }
 public int mark(position pos, int value) {
 return mark(pos.i(), pos.j(), value);
 }

 public boolean isMarked(int i, int j) {
 assert (isInMaze(i, j));
 return (maze[i][j] == p);
 }
 public boolean isMarked(position pos) {
 return isMarked(pos.i(), pos.j());
 }

 public boolean isClear(int i, int j) {
 assert (isInMaze(i, j));
 return (maze[i][j] != b && maze[i][j] != p);
 }
 public boolean isClear(position pos) {
 return isClear(pos.i(), pos.j());
 }

 public boolean isFinal(int i, int j) {
 return (i == Maze.end_i && j == Maze.end_j);
 }
 public boolean isFinal(position pos) {
 return isFinal(pos.i(), pos.j());
 }

 public int[][] clone() {
 int[][] mazeCopy = new int[Size()][Size()];
 for (int i = 0; i < Size(); i++) {
 for (int j = 0; j < Size(); j++) {
 mazeCopy[i][j] = maze[i][j];
 }
 }
 return mazeCopy;
 }
 
 
 public void restore(int[][] savedMazed) {
 for (int i = 0; i < Size(); i++) {
 for (int j = 0; j < Size(); j++) {
 maze[i][j] = savedMazed[i][j];
 }
 }
 maze[1][1] = 2;
 maze[2][9] = 8;
 }


 public int[][] GenerateArray() {
 arr = new int[10][10];
 Random rnd = new Random();
 int min = 0;
 int high = 1;
 for (int i = 0; i < 10; i++) {
 for (int j = 0; j < 10; j++) {
 int n = rnd.nextInt((high - min) + 1);
 arr[i][j] = n;

 }
 }
 arr[0][1] = 0;arr[1][0] = 0;arr[2][1] = 0;arr[1][2] =0;
 arr[1][9] = 0;arr[2][8] = 0;arr[3][9] = 0; 
 return arr;
 }


 @Override
 public void paint(Graphics g) {
 super.paint(g);
 g.translate(70,70);

 if (repaint == true) {
 for (int row = 0; row < maze.length; row++) {
 for (int col = 0; col < maze[0].length; col++) {
 Color color;
switch (maze[row][col]) {
 case 1:
 color = Color.black; 
 break;
 case 8:
 color = Color.red; 
 break;
 case 2:
 color = Color.yellow; 
 break;
default:
 color = Color.white; 

 }
g.setColor(color);
 g.fillRect(40 * col, 40 * row, 40, 40); 
 g.setColor(Color.blue);
 g.drawRect(40 * col, 40 * row, 40, 40); 
 }
 }
 }
 if (repaint == false) {
 for (int row = 0; row < maze.length; row++) {
 for (int col = 0; col < maze[0].length; col++) {
 Color color;
switch (maze[row][col]) {
 case 1:
 color = Color.black;
 break;
 case 8:
 color = Color.red;
 break;
 case 2:
 color = Color.yellow;
 break;
 case 9:
 color = Color.green; 
 break;
 default:
 color = Color.white;
 }
g.setColor(color);
 g.fillRect(40 * col, 40 * row, 40, 40);
 g.setColor(Color.BLUE);
 g.drawRect(40 * col, 40 * row, 40, 40);
 }
 }
 }
 }
 
 
 public void stack() {
 startTime = System.nanoTime();
 Stack<position> stack = new Stack<position>();
 stack.push(new position(start_i, start_j));
 position crt, next; 
 while (!stack.empty()) {
 crt = stack.pop();
 if (isFinal(crt)) {
 break;
 }
 mark(crt, p);
 next = crt.north(); 
 if (isInMaze(next) && isClear(next)) {
 stack.push(next);
 }
 next = crt.east(); 
 if (isInMaze(next) && isClear(next)) {
 stack.push(next);
 }
 next = crt.west(); 
 if (isInMaze(next) && isClear(next)) {
 stack.push(next);
 }
 next = crt.south(); 
 if (isInMaze(next) && isClear(next)) {
 stack.push(next);
 }
 }
 if (!stack.empty()) {
 stopTime = System.nanoTime();
 JOptionPane.showMessageDialog(null, "Source to Destination Path Found");
 } else {
 JOptionPane.showMessageDialog(null, "Source to Destination Path not Found");
 }
 
 System.out.println("\nFind Goal By DFS : ");
 Print();
 
 duration = stopTime - startTime; 
 dfsTime = (double)duration / 1000000; 
 System.out.println(String.format("Time: %1.3f ms", dfsTime));
 textDfs.setText(String.format("%1.3f ms", dfsTime));
 }
 
 
 public void queue() {
 startTime = System.nanoTime();
 Queue<position> list = new LinkedList<position>();
 list.add(new position(start_i, start_j));
 position crt, next;
 while (!list.isEmpty()) {
 crt = list.remove();
 if (isFinal(crt)) {
 break;
 }
 mark(crt, p);
 next = crt.north(); 
 if (isInMaze(next) && isClear(next)) {
 list.add(next);
 }
 next = crt.east(); 
 if (isInMaze(next) && isClear(next)) {
 list.add(next);
 }
 next = crt.west(); 
 if (isInMaze(next) && isClear(next)) {
 list.add(next);
 }
 next = crt.south();
 if (isInMaze(next) && isClear(next)) {
 list.add(next);
 }
 }
 if (!list.isEmpty()) {
 stopTime = System.nanoTime(); 
 JOptionPane.showMessageDialog(null, "Source to Destination Path Found");
 } else {
 JOptionPane.showMessageDialog(null, "Source to Destination Path not Found");
 }
 System.out.println("\nFind Goal By BFS : ");
 Print();
 
 duration = stopTime - startTime; 
 
 bfsTime = (double) duration / 1000000; 
 System.out.println(String.format("Time: %1.3f ms", bfsTime));
 textBFS.setText(String.format("%1.3f ms", bfsTime));
 
 }

}
