// Generated code from Butter Knife. Do not modify!
package com.happy.radiostation.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.happy.radiostation.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class RadioAdapter$RadioViewHolder_ViewBinding<T extends RadioAdapter.RadioViewHolder> implements Unbinder {
  protected T target;

  @UiThread
  public RadioAdapter$RadioViewHolder_ViewBinding(T target, View source) {
    this.target = target;

    target.nameTextView = Utils.findRequiredViewAsType(source, R.id.name_text_view, "field 'nameTextView'", TextView.class);
    target.playImageView = Utils.findRequiredViewAsType(source, R.id.playImageView, "field 'playImageView'", ImageView.class);
    target.favImageView = Utils.findRequiredViewAsType(source, R.id.favImageView, "field 'favImageView'", ImageView.class);
    target.typeTextView = Utils.findRequiredViewAsType(source, R.id.type_text_view, "field 'typeTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.nameTextView = null;
    target.playImageView = null;
    target.favImageView = null;
    target.typeTextView = null;

    this.target = null;
  }
}
