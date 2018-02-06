package com.huypham.snack.objectbox.ui.main;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.huypham.snack.objectbox.R;
import com.huypham.snack.objectbox.model.Animal;
import com.huypham.snack.objectbox.pref.App;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.Query;

/**
 * Snack project_object box
 * Created by HuyPhamNA on 01/02/2018.
 */

public class MainActivity extends AppCompatActivity implements AnimalAdapter.OnItemClickListener {
    private static final String TAG = "TAG";

    private List<Animal> mAnimals;
    private Box<Animal> mAnimalBox;
    private Query<Animal> mAnimalQuery;
    private AnimalAdapter mAnimalAdapter;

    private RecyclerView mRecyclerViewAnimal;
    private TextView mTvInform;
    private Button mBtnAdd;
    private Button mBtnDeleteAll;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBox();
        initView();
        initRecyclerViewAnimal();

    }

    private void initBox() {
        BoxStore boxStore = App.getApp().getBoxStore();
        mAnimalBox = boxStore.boxFor(Animal.class);
//        mAnimalQuery = mAnimalBox.query().order(Animal_.name).build();
    }

    private void initView() {
        mRecyclerViewAnimal = findViewById(R.id.recyclerAnimal);
        mTvInform = findViewById(R.id.tvInform);
        mBtnAdd = findViewById(R.id.btnAdd);
        mBtnDeleteAll = findViewById(R.id.btnDeleteAll);
        addAnimal();
        deleteAllAnimal();
    }

    private void initRecyclerViewAnimal() {
        getAllAnimal();

        mAnimalAdapter = new AnimalAdapter(mAnimals, this, this);
        mRecyclerViewAnimal.setHasFixedSize(true);
        mRecyclerViewAnimal.setLayoutManager(new LinearLayoutManager(this));
        if (mAnimals.size() == 0) {
            mTvInform.setVisibility(View.VISIBLE);
            mRecyclerViewAnimal.setVisibility(View.GONE);
        } else {
            mTvInform.setVisibility(View.GONE);
            mRecyclerViewAnimal.setVisibility(View.VISIBLE);
            mRecyclerViewAnimal.setAdapter(mAnimalAdapter);
            mAnimalAdapter.notifyDataSetChanged();
        }
    }

//    private void setDataListAnimal() {
////        mAnimals = mAnimalQuery.find();
//        mAnimals = mAnimalBox.getAll();
//    }

    private void getAllAnimal() {
        mAnimals = mAnimalBox.getAll();
    }

    @SuppressLint("InflateParams")
    private void addAnimal() {
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View dialogCreate = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_animal, null);
                final EditText edtName = dialogCreate.findViewById(R.id.edtName);
                final EditText edtDes = dialogCreate.findViewById(R.id.edtDescription);

                AlertDialog.Builder createAnimal = new AlertDialog.Builder(MainActivity.this);
                createAnimal.setTitle("Create animal")
                        .setView(dialogCreate)
                        .setMessage("Add new animal")
                        .setPositiveButton(R.string.button_add, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String name = edtName.getText().toString().trim();
                                String description = edtDes.getText().toString().trim();
                                Animal animal = new Animal();
                                animal.setName(name);
                                animal.setDescription(description);
                                Log.d(TAG, "onClick: 1");
                                mAnimalBox.put(animal);
                                Log.d(TAG, "onClick: 2");
                                initRecyclerViewAnimal();
                                Log.d(TAG, "onClick: 4");
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create()
                        .show();
            }
        });
    }

    private void deleteAllAnimal() {
        mBtnDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder deleteAll = new AlertDialog.Builder(MainActivity.this);
                deleteAll.setTitle("Delete all animal")
                        .setMessage("Do you want to delete all?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mAnimalBox.remove(mAnimals);
                                initRecyclerViewAnimal();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create()
                        .show();

            }
        });
    }

    @Override
    public void onItemClick(final int position) {
        AlertDialog.Builder removeAnimal = new AlertDialog.Builder(MainActivity.this);
        removeAnimal.setTitle("Delete animal")
                .setMessage("Do you want to delete animal at " + position)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Animal animal = mAnimals.get(position);
                        mAnimalBox.remove(animal);
                        initRecyclerViewAnimal();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();

    }

    @SuppressLint("InflateParams")
    @Override
    public boolean onItemLongClick(final int position) {
        final Animal animal = mAnimals.get(position);
        View dialogUpdate = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_animal, null);
        final EditText edtName = dialogUpdate.findViewById(R.id.edtName);
        final EditText edtDes = dialogUpdate.findViewById(R.id.edtDescription);
        edtName.setText(animal.getName());
        edtDes.setText(animal.getDescription());
        edtName.requestFocus();
        edtDes.requestFocus();

        AlertDialog.Builder updateAnimal = new AlertDialog.Builder(MainActivity.this);
        updateAnimal.setTitle("Update animal")
                .setMessage("Do you want to edit animal at " + position)
                .setView(dialogUpdate)
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = edtName.getText().toString().trim();
                        String description = edtDes.getText().toString().trim();
                        animal.setName(name);
                        animal.setDescription(description);
                        mAnimalBox.put(animal);
                        initRecyclerViewAnimal();
                    }
                })
                .setNegativeButton("Cancel", null)
                .create()
                .show();

        return true;
    }
}
