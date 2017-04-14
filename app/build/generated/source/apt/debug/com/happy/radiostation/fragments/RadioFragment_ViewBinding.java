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

public class RadioFragment_ViewBinding<T extends RadioFragment> implements Unbinder {
  protected T target;

  @UiThread
  public RadioFragment_ViewBinding(T target, View source) {
    this.target = target;

    target.animationImage = Utils.findRequiredViewAsType(source, R.id.animationImage, "field 'animationImage'", ImageView.class);
    target.titleTextView = Utils.findRequiredViewAsType(source, R.id.titleTextView, "field 'titleTextView'", TextView.class);
    target.typeTextView = Utils.findRequiredViewAsType(source, R.id.typeTextView, "field 'typeTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.animationImage = null;
    target.titleTextView = null;
    target.typeTextView = null;

    this.target = null;
  }
}
