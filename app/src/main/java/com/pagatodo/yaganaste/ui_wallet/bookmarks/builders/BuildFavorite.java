package com.pagatodo.yaganaste.ui_wallet.bookmarks.builders;

import android.content.Context;
import com.google.android.material.textfield.TextInputLayout;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.pagatodo.yaganaste.R;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions.CompositeLayoutAddFavorites;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions.CompositeShell;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions.InputDataNode;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions.SpinnerNode;
import com.pagatodo.yaganaste.ui_wallet.bookmarks.compositions.TextNode;
import com.pagatodo.yaganaste.ui_wallet.holders.InputDataViewHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.LauncherHolder;
import com.pagatodo.yaganaste.ui_wallet.holders.OnClickItemHolderListener;
import com.pagatodo.yaganaste.utils.customviews.StyleTextView;

public class BuildFavorite {

    private Context context;

    public BuildFavorite(Context context){
        this.context = context;
    }

    public TextNode setText(int t){
        return new TextNode(this.context,new StyleTextView(context),t);
    }

    public InputDataNode setInputData(int res, int resR){
        return new InputDataNode(this.context,new LinearLayout(context),res,resR);
    }

    public SpinnerNode setSpinnerNode(int res){
        return new SpinnerNode(this.context,new LinearLayout(context),res);
    }

    private Component addFavoriteArray(){
        Component c = new CompositeShell();
        c.add(setText(R.string.addFavorites));
        c.add(setInputData(R.string.favorte_name,0));
        return c;
    }

    public Component addFavoriteLayout(){
        Component c = new CompositeLayoutAddFavorites(new LinearLayout(context), R.color.colorAccent);
        c.add(addFavoriteArray());
        return c;
    }



    public static void build(FavoriteBuilder s, Object object, LauncherHolder i, OnClickItemHolderListener listener){
        s.addView(i,object,listener);
    }

}
