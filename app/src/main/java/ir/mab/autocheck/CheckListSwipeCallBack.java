package ir.mab.autocheck;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class CheckListSwipeCallBack extends ItemTouchHelper.SimpleCallback {

    private CheckListAdapter mAdapter;
    private Drawable deleteIcon;
    private Drawable editIcon;
    private ColorDrawable background;

    public CheckListSwipeCallBack(CheckListAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
        deleteIcon = ContextCompat.getDrawable(mAdapter.getContext(),
                R.drawable.ic_baseline_delete_forever_24);
        editIcon = ContextCompat.getDrawable(mAdapter.getContext(),
                R.drawable.ic_baseline_edit_24);
        background = new ColorDrawable(Color.WHITE);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();

        if (direction == ItemTouchHelper.RIGHT){
            mAdapter.editItem(position);
        }
        else {
            mAdapter.deleteItem(position);
        }
    }


    @Override
    public void onChildDraw(Canvas c,
                            RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder,
                            float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {

        super.onChildDraw(c, recyclerView, viewHolder, dX,
                dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;

        int iconMarginDelete = (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
        int iconTopDelete = itemView.getTop() + (itemView.getHeight() - deleteIcon.getIntrinsicHeight()) / 2;
        int iconBottomDelete = iconTopDelete + deleteIcon.getIntrinsicHeight();

        int iconMarginEdit = (itemView.getHeight() - editIcon.getIntrinsicHeight()) / 2;
        int iconTopEdit  = itemView.getTop() + (itemView.getHeight() - editIcon.getIntrinsicHeight()) / 2;
        int iconBottomEdit  = iconTopEdit + editIcon.getIntrinsicHeight();

        if (dX > 0) { // Swiping to the right
            int iconRight = itemView.getLeft() + iconMarginEdit + editIcon.getIntrinsicWidth();
            int iconLeft = itemView.getLeft() + iconMarginEdit;
            editIcon.setBounds(iconLeft, iconTopEdit, iconRight, iconBottomEdit);
            background.setBounds(itemView.getLeft(), itemView.getTop(),
                    itemView.getLeft() + ((int) dX) + backgroundCornerOffset,
                    itemView.getBottom());
        } else if (dX < 0) { // Swiping to the left
            int iconLeft = itemView.getRight() - iconMarginDelete - deleteIcon.getIntrinsicWidth();
            int iconRight = itemView.getRight() - iconMarginDelete;
            deleteIcon.setBounds(iconLeft, iconTopDelete, iconRight, iconBottomDelete);

            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset,
                    itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0);
        }

        background.draw(c);
        deleteIcon.draw(c);
        editIcon.draw(c);

    }

}
