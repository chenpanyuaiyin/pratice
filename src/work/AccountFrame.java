package work;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import work.Demo;
/**
 * Created by 尹恬婧 on 2018/7/13.
 */
public class AccountFrame extends JFrame{
    //上部放置查询相关组件
    private JPanel panelSearch=new JPanel();

    private JPanel panelProcess=new JPanel();
    private JTextField txtSearch=new JTextField();
    private JButton btnSearch=new JButton("search");
    private JButton btnProces=new JButton("add");
    private JButton btnDelete=new JButton("delete");
    private JButton btnModify=new JButton("modify");
    private JButton btnEnter=new JButton("save");
    private JButton btnReturn=new JButton("return");



    private JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    private JTable table=new JTable();
    Demo demo=new Demo();



    //添加面板panelAdd
    private JPanel panelAdd=new JPanel();
    private JLabel labAddAcount=new JLabel("账号");
    private JTextField txtAddAcount=new JTextField();
    private JLabel labAddPassword=new JLabel("密码");
    private JTextField txtAddPassword=new JTextField();


    //新面板panelContent
    JPanel panelContent = new JPanel();

    private void initTable(String keyword) {
        String [][] datas = null;
        //2. 如果为空，这查询所有数据，否则按关键字搜索。
        if(keyword == null || keyword.trim().equals("")) {
            datas = demo.bestFindAllData();
        } else {
            datas = demo. bestFindDatalikeKey(keyword);
        }
        table.setModel(getTableModel(datas));
    }

    private DefaultTableModel getTableModel (Object [][] rowDatas){
        DefaultTableModel model=new DefaultTableModel();
        model.addColumn("id");
        model.addColumn("账号");
        model.addColumn("密码");
        for (Object [] data :rowDatas){
            model.addRow(data);
        }
        return model;
    }

    public AccountFrame(){
        panelContent.setLayout(new BorderLayout());
        this.setContentPane(panelContent);
        //初始化组件
        panelSearch.setLayout(new BorderLayout());
        panelSearch.add(txtSearch);
        initTable("");
        scrollPane.getViewport().add(table);
        panelContent.add(scrollPane);


        //添加上方按钮
        panelSearch.add(btnSearch,BorderLayout.EAST);

        //初始化panelAdd面板组件
        panelAdd.setLayout(new GridLayout(2,2));
        panelAdd.add(labAddAcount);
        panelAdd.add(txtAddAcount);
        panelAdd.add(labAddPassword);
        panelAdd.add(txtAddPassword);
        // panelAdd.setVisible(false);


        //添加下方点击按钮
        panelProcess.add(btnProces);
        panelProcess.add(btnDelete);
        panelProcess.add(btnModify);
        panelProcess.add(btnEnter);
        btnEnter.setVisible(false);
        panelProcess.add(btnReturn);
        btnReturn.setVisible(false);


        //Add按钮的事件
        btnProces.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=table.getSelectedRow();
                Object value=table.getValueAt(row,0);
                String name=(String)table.getValueAt(row,1);
                String password=(String)table.getValueAt(row,2);
                //int id=Integer.parseInt(table.getValueAt(row,0)+"");
                demo.insertData(name,password);
                initTable(txtSearch.getText());
                        }


                /** AccountFrame.this.panelContent.remove(txtCenter);
                 AccountFrame.this.panelContent.add(panelAdd);
                 panelAdd.setVisible(true);
                 btnEnter.setVisible(true);
                 btnReturn.setVisible(true);
                 btnDelete.setVisible(false);
                 btnProces.setVisible(false);
                 btnModify.setVisible(false);
                 */

                //拿到文本内容，根据这个内容决定
                /**String text=btnProces.getText();
                if (text.equals("add")){
                    //进入添加记录的操作
                    // AccountFrame.this.panelContent.remove(txtCenter);
                    AccountFrame.this.panelContent.add(panelAdd);
                    text="save";

                    btnDelete.setText("cancel");
                    btnModify.setVisible(false);
                }else {
                    text="add";
                    AccountFrame.this.panelContent.remove(panelAdd);
                    // AccountFrame.this.panelContent.add(txtCenter);
                    // txtCenter.setText("保存成功!");
                    btnDelete.setText("delete");
                    btnModify.setVisible(true);
                }
                btnProces.setText(text);
                //类似与页面刷新
                AccountFrame.this.panelContent.setVisible(false);
                AccountFrame.this.panelContent.setVisible(true);
*/

        });

        btnModify.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row=table.getSelectedRow();
                if (row<0){
                    JOptionPane.showMessageDialog(null,"请选择需要修改的数据","提示",JOptionPane.WARNING_MESSAGE);
                }else {
                    int option=JOptionPane.showConfirmDialog(AccountFrame.this,"确定要修改选择的数据吗？",
                            "确认修改",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if (option==0){
                        Object value=table.getValueAt(row,0);
                        if (value !=null && !(value+"").trim().equals("")){
                            //取消删除 1.拿到用户选择的数据
                            String name=(String)table.getValueAt(row,1);
                            String password=(String)table.getValueAt(row,2);
                            int id=Integer.parseInt(table.getValueAt(row,0)+"");
                            demo.testUpdateData(name,password,id);
                            initTable(txtSearch.getText());

                        }
                    }

                }
                //String textDelete=btnDelete.getText();
            }
        });


        btnDelete.addActionListener(new ActionListener() {
            @Override

           public void actionPerformed(ActionEvent e) {
                int row=table.getSelectedRow();
                if (row<0){
                    JOptionPane.showMessageDialog(null,"请选择需要删除的数据","tishi",JOptionPane.WARNING_MESSAGE);
                }else {
                    int option=JOptionPane.showConfirmDialog(AccountFrame.this,"确定要删除要选择的数据吗？",
                            "删除确认",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if (option==0){
                        Object value=table.getValueAt(row,0);
                        if (value !=null && !(value+"").trim().equals("")){
                            //取消删除 1.拿到用户选择的数据
                            int id=Integer.parseInt(table.getValueAt(row,0)+"");
                            demo.testDeleteDate(id);
                            initTable(txtSearch.getText());

                        }
                    }

                }
                /**String textDelete=btnDelete.getText();
                if (textDelete.equals("cancel")){
                    //返回主界面
                    AccountFrame.this.panelContent.remove(panelAdd);
                    //AccountFrame.this.panelContent.add(txtCenter);
                    //改变按钮
                    btnModify.setVisible(true);
                    btnDelete.setText("delete");
                    //改变文本内容
                    //txtCenter.setText(null);
                    //刷新
                    AccountFrame.this.panelContent.setVisible(false);
                    AccountFrame.this.panelContent.setVisible(true);

                }*/
            }
        });

        //reture按钮的事件
        /**  btnReturn.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        AccountFrame.this.panelContent.remove(panelAdd);
        AccountFrame.this.panelContent.add(txtCenter);
        txtCenter.setVisible(true);
        btnEnter.setVisible(false);
        btnReturn.setVisible(false);
        btnDelete.setVisible(true);
        btnProces.setVisible(true);
        btnModify.setVisible(true);
        AccountFrame.this.panelContent.setVisible(false);
        AccountFrame.this.panelContent.setVisible(true);
        }
        });
         */
        //search按钮的事件
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //1. 获得用户输入的文本内容
                String keyword = txtSearch.getText();
                initTable(keyword);
            }
        });

        txtSearch.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                initTable(txtSearch.getText());

            }
        });

        panelContent.add(panelSearch, BorderLayout.NORTH);
        panelContent.add(panelProcess, BorderLayout.SOUTH);
        //panelContent.add(txtCenter);



        //设定窗体相关属性
        this.setSize(800,600);
        this.setTitle("账号首页");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
    public static void main(String... args){
        new AccountFrame();

    }
}
