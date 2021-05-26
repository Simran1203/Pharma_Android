package  com.pharmacy.crack.main.view.LoginSignUpActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.pharmacy.crack.R
import com.pharmacy.crack.databinding.ActivityLoginBinding
import com.pharmacy.crack.databinding.ActivityStoryBinding
import com.pharmacy.crack.main.view.mainActivities.DashboardActivity
import com.pharmacy.crack.utils.setFullScreen
import kotlinx.android.synthetic.main.activity_story.*

class StoryActivity : AppCompatActivity(), View.OnClickListener {
    private var doubleBackToExitPressedOnce = false
    private lateinit var binding: ActivityStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreen(this)
        binding = DataBindingUtil.setContentView(this@StoryActivity, R.layout.activity_story)
        binding.lifecycleOwner = this;

        onClicklistner()
    }

    private fun onClicklistner() {
        binding.txtSkip.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
      if(v == binding.txtSkip){
        startActivity(Intent(this, DashboardActivity::class.java))
      }
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
           finishAffinity()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }
}