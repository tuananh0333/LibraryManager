package databases;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import data_modals.UserModal;

public class UserDatabase {
    private final String NODE_NAME = "users";
    private final String USER_NAME = "name";
    private final String USER_PASS = "pass";

    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference(NODE_NAME);

    private void writeNewUser(UserModal user) {
        mDatabase.child("users").child(user.getUserId()).child(USER_NAME).setValue(user.getName());
        mDatabase.child("users").child(user.getUserId()).child(USER_PASS).setValue(user.getPassword());
    }

    ValueEventListener userListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            UserModal userList = dataSnapshot.getValue(UserModal.class);

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };
}
