package earliest_deadline_first;

import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_MODENA;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import earliest_deadline_first.InformationEnter;
import earliest_deadline_first.Process;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class earliest_deadline_first_class extends Application {

    @Override
    public void start(Stage primaryStage) {
        Process proc1 = new Process();
        Process proc2 = new Process();
        Process proc3 = new Process();
        BorderPane borderPane = new BorderPane();
       

        Line l = new Line(23, 400, 980, 400);
        l.setFill(Color.BLACK);
        l.setStroke(Color.BLACK);
        Pane root = new Pane();
        InformationEnter t = new InformationEnter();
        t.tf1.setFont(Font.font("SanSerif", 13));
        t.tf1.setPromptText("execution time for A");
        t.tf1.getStyleClass().add("field-back");
        t.tf1.setMaxWidth(300);
        
        t.tf2.setFont(Font.font("SanSerif", 13));
        t.tf2.setPromptText("execution time for B");
        t.tf2.getStyleClass().add("field-back");
        t.tf2.setMaxWidth(300);
        
        t.tf3.setFont(Font.font("SanSerif", 13));
        t.tf3.setPromptText("execution time for C");
        t.tf3.getStyleClass().add("field-back");
        t.tf3.setMaxWidth(300);
        
        t.tf4.setFont(Font.font("SanSerif", 13));
        t.tf4.setPromptText("Period for A");
        t.tf4.getStyleClass().add("field-back");
        t.tf4.setMaxWidth(300);
        
        t.tf5.setFont(Font.font("SanSerif", 13));
        t.tf5.setPromptText("Period for B");
        t.tf5.getStyleClass().add("field-back");
        t.tf5.setMaxWidth(300);
        
        t.tf6.setFont(Font.font("SanSerif", 13));
        t.tf6.setPromptText("Period for C");
        t.tf6.getStyleClass().add("field-back");
        t.tf6.setMaxWidth(300);
        
        
        
        
        
        
        
        
       
        Button btn = new Button("Start CPU");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                int p1, p2, p3, p4, p5, p6;
                Integer[] a = t.returnValues();
                p1 = proc1.time = a[0];
                p2 = proc2.time = a[1];
                p3 = proc3.time = a[2];
                p4 = proc1.period = proc1.nextDeadLine = a[3];
                p5 = proc2.period = proc2.nextDeadLine = a[4];
                p6 = proc3.period = proc3.nextDeadLine = a[5];
                proc1.name = "P1";
                proc2.name = "P2";
                proc3.name = "P3";
                int lcm = calLcm(p4, p5, p6);
                int sector = 960 / lcm;
                int start = 20 + sector;
                int i = 0;
                Process fPirority, sPirority, thPirority;
                Process[] pir = calPir(p4, p5, p6, proc1, proc2, proc3);
                fPirority = pir[0];
                sPirority = pir[1];
                thPirority = pir[2];
                Process[] Ready = new Process[3];
                Process excutable = new Process();
                int fTime = fPirority.time;
                int sTime = sPirority.time;
                int thTime = thPirority.time;
                System.out.println("pro " + proc3.nextDeadLine + " read " + proc3.state);
                while (i < lcm) {
                    Line l1 = new Line(start, 395, start, 405);
                    Rectangle r = new Rectangle();
                    Text txt = new Text(Integer.toString(i + 1));
                    txt.setX(start);
                    txt.setY(420);
                    start += sector;

                    {
                        excutable = calEdf(proc1, proc2, proc3, i);
                        if (excutable == proc1) {
                            fTime--;
                            if (fTime == 0) {
                                fTime = proc1.time;
                                proc1.nextDeadLine += proc1.period;
                                proc1.state = "finished";
                            }
                            root.getChildren().add(drawP1(sector, i));
                        } else if (excutable == proc2) {
                            sTime--;
                            if (sTime == 0) {
                                sTime = proc2.time;
                                proc2.nextDeadLine += proc2.period;
                                proc2.state = "finished";
                            }
                            root.getChildren().add(drawP2(sector, i));
                        } else if (excutable == proc3) {
                            thTime--;
                            if (thTime == 0) {
                                thTime = proc3.time;
                                proc3.nextDeadLine += proc3.period;
                                proc3.state = "finished";
                            } else {
                                thPirority.state = "executing";
                            }
                            root.getChildren().add(drawP3(sector, i));
                        }
                    }

                    i++;
                    if (i % proc1.period == 0) {
                        proc1.state = "ready";
                    }
                    if (i % proc2.period == 0) {
                        proc2.state = "ready";
                    }
                    if (i % proc3.period == 0) {
                        proc3.state = "ready";
                    }
                    root.getChildren().addAll(l1, txt);
                }
            }
        });

        root.getChildren().addAll(l);
        borderPane.setCenter(root);
        borderPane.setTop(t.drawTable());
        borderPane.setRight(btn);
        btn.setAlignment(Pos.TOP_RIGHT);
        borderPane.setMargin(btn, new Insets(0, 20, 0, 0));
        btn.setTextFill(Color.BLACK);
        btn.setFont(Font.font(STYLESHEET_MODENA, FontWeight.BOLD, 20));
        
         StackPane sp = new StackPane();

        Image img = new Image("millimeter.jpg");

        ImageView imgView = new ImageView(img);

        sp.getChildren().addAll(imgView, borderPane);
        
        
        
        Scene scene = new Scene(sp, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Eerliest dead line first scheduling");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("schedule.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Process calEdf(Process p1, Process p2, Process p3, int i) {
        if ((p1.nextDeadLine) < (p2.nextDeadLine) && (p1.nextDeadLine) < (p3.nextDeadLine) && p1.state == "ready") {
            return p1;
        } else if (p2.nextDeadLine < p1.nextDeadLine && p2.nextDeadLine < p3.nextDeadLine && p2.state == "ready") {
            return p2;
        } else if (p3.nextDeadLine < p2.nextDeadLine && p3.nextDeadLine < p1.nextDeadLine && (p3.state == "ready" || p3.state == "executing")) {
            return p3;
        } else if (p1.nextDeadLine == p2.nextDeadLine) {
            if (p1.state == "ready" && p2.state == "ready") {
                if (p1.pir < p2.pir) {
                    return p1;
                } else {
                    return p2;
                }
            } else if ((p1.state == "ready" || p1.state == "executing") && p2.state == "finished") {
                return p1;
            } else if ((p2.state == "ready" || p2.state == "executing") && p1.state == "finished") {
                return p2;
            } else if (p1.state == "executing" && p2.state == "ready") {
                return p1;
            } else if (p2.state == "executing" && p1.state == "ready") {
                return p2;
            } else {
                return null;
            }
        } else if (p1.nextDeadLine == p3.nextDeadLine) {
            if (p1.state == "ready" && p3.state == "ready") {
                if (p1.pir < p3.pir) {
                    return p1;
                } else {
                    return p3;
                }
            } else if ((p1.state == "ready" || p1.state == "executing") && p3.state == "finished") {
                return p1;
            } else if ((p3.state == "ready" || p3.state == "executing") && p1.state == "finished") {
                return p3;
            } else if (p1.state == "executing" && p3.state == "ready") {
                return p1;
            } else if (p3.state == "executing" && p1.state == "ready") {
                return p3;
            } else {
                return null;
            }
        } else if (p2.nextDeadLine == p3.nextDeadLine) {
            if (p2.state == "ready" && p3.state == "ready") {
                if (p2.pir < p3.pir) {
                    return p2;
                } else {
                    return p3;
                }
            } else if ((p2.state.equals("ready") || p1.state.equals("executing")) && p3.state.equals("finished")) {
                return p2;
            } else if ((p3.state == "ready" || p3.state == "executing") && p2.state == "finished") {
                return p3;
            } else if (p2.state == "executing" && p3.state == "ready") {
                return p2;
            } else if (p3.state == "executing" && p2.state == "ready") {
                return p3;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public Rectangle drawP1(int sector, int i) {
        Rectangle r = new Rectangle();
        r.setX(20 + sector * i);
        r.setY(70);
        r.setWidth(sector);
        r.setHeight(30);
        r.setFill(Color.YELLOW);
        
        return r;
    }

    public Rectangle drawP2(int sector, int i) {
        Rectangle r = new Rectangle();
        r.setX(20 + sector * i);
        r.setY(170);
        r.setWidth(sector);
        r.setHeight(30);
        r.setFill(Color.RED);
      

        return r;
    }

    public Rectangle drawP3(int sector, int i) {
        Rectangle r = new Rectangle();
        r.setX(20 + sector * i);
        r.setY(270);
        r.setWidth(sector);
        r.setHeight(30);
        r.setFill(Color.YELLOWGREEN);

        return r;
    }

    public int calLcm(int i, int j, int k) {
        int lcm = 0;
        if (i % j == 0 && i % k == 0) {
            lcm = i;
        } else if (j % i == 0 && j % k == 0) {
            lcm = j;
        } else if (k % j == 0 && k % i == 0) {
            lcm = k;
        } else if ((i * j) % k == 0) {
            lcm = i * j;
        } else if ((j * k) % i == 0) {
            lcm = j * k;
        } else if ((i * k) % j == 0) {
            lcm = i * k;
        } else {
            lcm = j * k * i;
        }
        return lcm;
    }

    public Process[] calPir(int p4, int p5, int p6, Process proc1, Process proc2, Process proc3) {
        Process[] pir = new Process[3];
        if (p4 < p5 && p5 < p6) {
            pir[0] = proc1;
            pir[1] = proc2;
            pir[2] = proc3;
            proc1.pir = 1;
            proc2.pir = 2;
            proc3.pir = 3;
        } else if (p4 < p6 && p6 < p5) {
            pir[0] = proc1;
            pir[1] = proc3;
            pir[2] = proc2;
            proc1.pir = 1;
            proc3.pir = 2;
            proc2.pir = 3;
        } else if (p5 < p4 && p4 < p6) {
            pir[0] = proc2;
            pir[1] = proc1;
            pir[2] = proc3;
            proc2.pir = 1;
            proc1.pir = 2;
            proc3.pir = 3;
        } else if (p5 < p6 && p6 < p4) {
            pir[0] = proc2;
            pir[1] = proc3;
            pir[2] = proc1;
            proc2.pir = 1;
            proc3.pir = 2;
            proc1.pir = 3;
        } else if (p6 < p4 && p4 < p5) {
            pir[0] = proc3;
            pir[1] = proc1;
            pir[2] = proc2;
            proc3.pir = 1;
            proc1.pir = 2;
            proc2.pir = 3;
        } else if (p6 < p5 && p5 < p4) {
            pir[0] = proc3;
            pir[1] = proc2;
            pir[2] = proc1;
            proc3.pir = 1;
            proc2.pir = 2;
            proc1.pir = 3;
        }
        return pir;
    }
}



