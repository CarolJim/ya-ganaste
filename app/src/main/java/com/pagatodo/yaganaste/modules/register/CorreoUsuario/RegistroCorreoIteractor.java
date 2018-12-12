package com.pagatodo.yaganaste.modules.register.CorreoUsuario;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.pagatodo.yaganaste.App;
import com.pagatodo.yaganaste.data.model.RegisterUserNew;

import java.util.HashMap;

import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE_AUTH;
import static com.pagatodo.yaganaste.utils.Recursos.TOKEN_FIREBASE_SESSION;
import static com.pagatodo.yaganaste.utils.Recursos.URL_BD_ODIN_USERS;

public class RegistroCorreoIteractor implements RegistroCorreoContracts.Iteractor {

    @Override
    public void registerFirebase() {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(RegisterUserNew.getInstance().getEmail(), "123456").addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = auth.getCurrentUser();
                App.getInstance().getPrefs().saveData(TOKEN_FIREBASE_AUTH, user.getUid());
                HashMap users = new HashMap<String, String>();
                users.put("Mbl", "00000");
                users.put("DvcId", FirebaseInstanceId.getInstance().getId());
                FirebaseDatabase.getInstance(URL_BD_ODIN_USERS).getReference().child(user.getUid()).setValue(users);
                getFirebaseSessionId();
            }
        });
    }

    private void getFirebaseSessionId() {
        FirebaseAuth.getInstance().getCurrentUser().getIdToken(true).addOnCompleteListener(task -> {
            if (task.isSuccessful())
                App.getInstance().getPrefs().saveData(TOKEN_FIREBASE_SESSION, task.getResult().getToken());
        });
    }
}
