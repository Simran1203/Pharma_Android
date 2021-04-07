package  com.pharmacy.crack.main.view.LoginSignUpActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.pharmacy.crack.R
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_story.*

class StoryActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        setContentView(R.layout.activity_story)

        onClicklistner()
    }

    private fun onClicklistner() {
        txtSkip.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
      if(v== txtSkip){
        startActivity(Intent(this, DashboardActivity::class.java))
      }
    }
}