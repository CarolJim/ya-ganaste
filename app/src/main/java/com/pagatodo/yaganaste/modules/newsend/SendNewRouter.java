package com.pagatodo.yaganaste.modules.newsend;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.interfaces.enums.Direction;
import com.pagatodo.yaganaste.modules.newsend.AllFavorites.AllFavoritesFragment;
import com.pagatodo.yaganaste.modules.newsend.SendFromCard.SendFromCardFragment;
import com.pagatodo.yaganaste.modules.register.CorreoUsuario.RegistroCorreoFragment;

public class SendNewRouter  implements SendNewContracts.Router {

    SendNewActivity activity;


    public SendNewRouter(SendNewActivity activity) {
        this.activity = activity;
    }



    @Override
    public void showAllFavorites(Direction direction) {
        activity.loadFragment(AllFavoritesFragment.newInstance(), R.id.fragment_container,
                direction, false);


    }

    @Override
    public void showsendfragment(Direction direction, int type) {
        activity.loadFragment(SendFromCardFragment.newInstance(type), R.id.fragment_container,
                direction, false);
    }
}
