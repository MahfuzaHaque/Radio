// Generated code from Butter Knife. Do not modify!
package com.happy.radiostation;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding<T extends MainActivity> implements Unbinder {
  protected T target;

  private View view2131558524;

  private View view2131558526;

  private View view2131558528;

  @UiThread
  public MainActivity_ViewBinding(final T target, View source) {
    this.target = target;

    View view;
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.view_pager, "field 'viewPager'", ViewPager.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'toolbar'", Toolbar.class);
    target.navigationView = Utils.findRequiredViewAsType(source, R.id.nav_view, "field 'navigationView'", NavigationView.class);
    target.drawer = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'drawer'", DrawerLayout.class);
    view = Utils.findRequiredView(source, R.id.previousImageButton, "field 'previousImageButton' and method 'onClick'");
    target.previousImageButton = Utils.castView(view, R.id.previousImageButton, "field 'previousImageButton'", ImageButton.class);
    view2131558524 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.playCheckBox = Utils.findRequiredViewAsType(source, R.id.playCheckBox, "field 'playCheckBox'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.nextImageBtton, "field 'nextImageBtton' and method 'onClick'");
    target.nextImageBtton = Utils.castView(view, R.id.nextImageBtton, "field 'nextImageBtton'", ImageButton.class);
    view2131558526 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.favoriteCheckBox = Utils.findRequiredViewAsType(source, R.id.favoriteCheckBox, "field 'favoriteCheckBox'", CheckBox.class);
    target.volumeSeekBar = Utils.findRequiredViewAsType(source, R.id.volume_seek_bar, "field 'volumeSeekBar'", SeekBar.class);
    target.volumeCheckBox = Utils.findRequiredViewAsType(source, R.id.volumeCheckBox, "field 'volumeCheckBox'", CheckBox.class);
    target.radioControlLayout = Utils.findRequiredViewAsType(source, R.id.radio_control_layout, "field 'radioControlLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.shareImageButton, "field 'shareImageButton' and method 'onClick'");
    target.shareImageButton = Utils.castView(view, R.id.shareImageButton, "field 'shareImageButton'", ImageButton.class);
    view2131558528 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    T target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");

    target.viewPager = null;
    target.toolbar = null;
    target.navigationView = null;
    target.drawer = null;
    target.previousImageButton = null;
    target.playCheckBox = null;
    target.nextImageBtton = null;
    target.favoriteCheckBox = null;
    target.volumeSeekBar = null;
    target.volumeCheckBox = null;
    target.radioControlLayout = null;
    target.shareImageButton = null;

    view2131558524.setOnClickListener(null);
    view2131558524 = null;
    view2131558526.setOnClickListener(null);
    view2131558526 = null;
    view2131558528.setOnClickListener(null);
    view2131558528 = null;

    this.target = null;
  }
}
