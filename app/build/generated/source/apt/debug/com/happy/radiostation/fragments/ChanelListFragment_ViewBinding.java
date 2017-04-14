// Generated code from Butter Knife. Do not modify!
package com.happy.radiostation.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.happy.radiostation.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ChanelListFragment_ViewBinding<T extends ChanelListFragment> implements Unbinder {
  protected T target;

  @UiThread
  public ChanelListFragment_ViewBinding(T target, View source) {
    this.target = target;

    target.mRadioRecyclerView = Utils.findRequiredViewAsType(source, R.id.radioRecylerView, "field 'mRadioRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.mRadioRecyclerView = null;

    this.target = null;
  }
}
