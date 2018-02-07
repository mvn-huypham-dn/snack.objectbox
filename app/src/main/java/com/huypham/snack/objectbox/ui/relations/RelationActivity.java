package com.huypham.snack.objectbox.ui.relations;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.widget.TextView;

import com.huypham.snack.objectbox.R;
import com.huypham.snack.objectbox.model.Customer;
import com.huypham.snack.objectbox.model.Order;
import com.huypham.snack.objectbox.model.Order_;
import com.huypham.snack.objectbox.model.Student;
import com.huypham.snack.objectbox.model.Teacher;
import com.huypham.snack.objectbox.pref.App;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.relation.ToMany;

/**
 * Snack project_object box
 * Created by HuyPhamNA on 07/02/2018.
 */

public class RelationActivity extends AppCompatActivity{
    private static final String TAG = "Relation";

    private BoxStore mBoxStore;
    private TextView mTvLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relations);

        mTvLog = findViewById(R.id.tvLog);
        mBoxStore = App.getApp().getBoxStore();

        long start = System.currentTimeMillis();
        ordersAndCustomers();
        studentsAndTeachers();
        long time = System.currentTimeMillis() - start;
        log("Done in " + time + "ms");
    }

    /**
     * Demonstrates a one-to-many (1:N) relation.
     * Order has a to-one to Customer.
     * Customer has a to-many backlink to Order.
     */
    private void ordersAndCustomers() {
        Box<Customer> customerBox = mBoxStore.boxFor(Customer.class);
        Box<Order> orderBox = mBoxStore.boxFor(Order.class);

        // Remove all previous object to have clear start for simplicity's sake
        customerBox.removeAll();
        orderBox.removeAll();

        logTitle("Add a customer with an order (using to-one)");
        Customer customer = new Customer();
        Customer customer10 = new Customer();
        Order order1 = new Order();
        order1.customer.setTarget(customer);
        orderBox.put(order1);
        logOrders(orderBox, customer);
//        logOrders(orderBox, customer10);

        logTitle("Add two orders to the customer (from the other side of the relation using the to-many backlink)");
        customer.orders.reset(); // just to be on the safe side before adding
        customer.orders.add(new Order());
        customer.orders.add(new Order());
        customerBox.put(customer);
        logOrders(orderBox, customer);

//        customer10.orders.reset(); // just to be on the safe side before adding
//        customer10.orders.add(new Order());
//        customer10.orders.add(new Order());
//        customerBox.put(customer10);
//        logOrders(orderBox, customer10);

        logTitle("Remove (delete) the first order object");
        orderBox.remove(order1);
        logOrders(orderBox, customer);

        logTitle("Remove an order from the to-many relation (does not delete the order object)");
        customer.orders.reset();
        customer.orders.remove(0);
        customerBox.put(customer);
        logOrders(orderBox, customer);
    }

    private void logOrders(Box<Order> orderBox, Customer customer) {
        List<Order> ordersQueried = orderBox.query().equal(Order_.customerId, customer.id).build().find();
        log("Customer " + customer.id + " has " + ordersQueried.size() + " orders");
        for (Order order : ordersQueried) {
            log("Order " + order.id + " related to customer " + order.customer.getTargetId());
        }
        log("");
    }

    /**
     * Demonstrates a to-many relation.
     * Student has a to-many to Teacher.
     */
    private void studentsAndTeachers() {
        Box<Student> studentBox = mBoxStore.boxFor(Student.class);
        Box<Teacher> teacherBox = mBoxStore.boxFor(Teacher.class);

        // Remove all previous object to have clear start for simplicity's sake
        studentBox.removeAll();
        teacherBox.removeAll();

        logTitle("Add two students and two teachers; first student has two teachers, second student has one teacher");
        Teacher teacher1 = new Teacher();
        Teacher teacher2 = new Teacher();
        Student student1 = new Student();
        student1.teachers.add(teacher1);
        student1.teachers.add(teacher2);
        Student student2 = new Student();
        student2.teachers.add(teacher1);
        studentBox.put(student1, student2);
        logTeachers(studentBox, teacherBox);

        logTitle("Remove first teacher from first student");
        student1.teachers.remove(teacher1);
        student1.teachers.applyChangesToDb(); // more efficient than studentBox.put(student1);
        logTeachers(studentBox, teacherBox);
    }

    private void logTeachers(Box<Student> studentBox, Box<Teacher> teacherBox) {
        log("There are " + teacherBox.count() + " teachers");
        List<Student> students = studentBox.getAll();
        for (Student student : students) {
            ToMany<Teacher> teachersToMany = student.teachers;
            for (Teacher teacher : teachersToMany) {
                log("Student " + student.id + " is taught by teacher " + teacher.id);
            }
        }
        log("");
    }

    private void log(String message) {
        Log.d(TAG, message);
        mTvLog.append(message + "\n");
    }

    private void logTitle(String message) {
        Log.d(TAG, ">>> " + message + " <<<");
        Spannable spannableString = new SpannableString(message.concat("\n"));
        StyleSpan span = new StyleSpan(Typeface.BOLD);
        spannableString.setSpan(span, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTvLog.append(spannableString);
    }
}
