package fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bean.FeedBeanList
import com.demo.swt.mystudyappshop.retrofit.RetrofitManager
import kotlinx.coroutines.launch


/**
 * introduce：here is introduce
 * author：sunwentao
 * email：wentao.sun@freebrio.com
 * data: 12/30/20
 */
class FriendViewModel() : ViewModel() {

    var data = MutableLiveData<FeedBeanList>()

    fun getFriend(nt: String) {
        launch({
            data.value = RetrofitManager.getInstance().getFriend(nt)
        }, {

        })
    }

    private fun launch(block: suspend () -> Unit, error: suspend (Throwable) -> Unit) = viewModelScope.launch {
        try {
            block()
        } catch (e: Throwable) {
            error(e)
        }
    }

}