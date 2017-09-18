package seven.com.neihan.baseClass

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar

/**
 * Created by Seven on 2017/7/24.
 */
abstract class BaseActivity: AppCompatActivity() {

    protected var toolBar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(onGetLayout())
        onSetNavigationBar()
        initInOnCreat()
    }

    override fun onDestroy() {
        super.onDestroy()
        deInitInOnDestroy()
    }

    fun onSetNavigationBar() {
        toolBar = onGetToolBar()
        toolBar?.setNavigationOnClickListener {
            backClick()
        }
        onSetToolBar()
    }

    // layout
    abstract fun onGetLayout(): Int

    // 界面初始化
    abstract fun initInOnCreat()

    // 销毁的时候的操作
    abstract fun deInitInOnDestroy()

    // 获取toolBar
    abstract fun onGetToolBar(): Toolbar

    // 根据每个activity不同设置toolBar
    abstract fun onSetToolBar()

    // toolBar返回按钮时间
    abstract fun backClick()


}
