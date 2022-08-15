package com.fast.app.ui.screen

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.fast.app.ui.componet.CommonDivider
import com.fast.app.ui.componet.CommonScreenTopBar
import com.fast.app.ui.componet.EmptyView
import com.fast.app.ui.componet.SetSystemBarsColor
import com.fast.app.viewmodel.LineViewModel
import com.fast.common.ui.theme.MyAppTheme
import com.fast.common.utils.NetworkImage
import com.fast.common.utils.Utils
import com.fast.common.viewmodel.BaseViewModel
import com.google.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LineScreen(navController: NavHostController, dataSize: Int) {
    val dataList: MutableList<String> = arrayListOf()
    for(i in 0..dataSize)dataList.add("StephenList$i")
    val curViewModel = viewModel<LineViewModel>()
    val appTheme by curViewModel.appTheme.collectAsState()
    MyAppTheme(appTheme = appTheme) {
        ProvideWindowInsets {
            SetSystemBarsColor(key = appTheme, statusBarColor = Color.Transparent, navigationBarColor = MaterialTheme.colors.primaryVariant)
            LaunchedEffect(Unit) {
                launch {
                    curViewModel.clickState.collect {
                        Utils.showToast("clickState:${it}")
                    }
                }
            }

            Scaffold(modifier = Modifier.fillMaxSize()) {
                it.calculateTopPadding()
                Column {
                    CommonScreenTopBar(centerTitle = "Stephen", onClickBackMenu = {
                        navController.currentBackStackEntry?.savedStateHandle?.set("backData", "LaLaLaLa.....")
                        navController.popBackStack() }, onClickMoreMenu = {
                        Utils.showToast("Nothing") })
                    if (dataList.isEmpty()) {
                        EmptyView()
                    } else {
                        LazyColumn {
                            dataList.forEachIndexed { index, item ->
                                item(key = "Key$index") {
                                    LineListItem(item, onClickCallBack = {
                                        curViewModel.clickState.value = BaseViewModel.CommonString(it)
                                    })
                                }
                            }
                            item {
                                Spacer(modifier = Modifier.height(40.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LineListItem(itemData: String, onClickCallBack: (String) -> Unit) {
    val padding = 12.dp
    ConstraintLayout(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClickCallBack(itemData) },) {
        val (avatar, showName, signature, divider) = createRefs()
        NetworkImage(data = "https://img0.baidu.com/it/u=2029936336,3680262465&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500",
            modifier = Modifier
                .padding(start = padding * 1.5f, top = padding, bottom = padding)
                .size(size = 50.dp)
                .clip(shape = CircleShape)
                .constrainAs(ref = avatar) {
                    start.linkTo(anchor = parent.start)
                    top.linkTo(anchor = parent.top)
                }
        )
        Text(text = itemData,
            style = MaterialTheme.typography.subtitle1,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .padding(start = padding, top = padding, end = padding)
                .constrainAs(ref = showName) {
                    start.linkTo(anchor = avatar.end)
                    top.linkTo(anchor = parent.top)
                    end.linkTo(anchor = parent.end)
                    width = Dimension.fillToConstraints
                }
        )
        Text(text = itemData,
            style = MaterialTheme.typography.subtitle2,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier
                .padding(start = padding, end = padding)
                .constrainAs(ref = signature) {
                    start.linkTo(anchor = showName.start)
                    top.linkTo(anchor = showName.bottom, margin = padding / 2)
                    end.linkTo(anchor = parent.end)
                    width = Dimension.fillToConstraints
                }
        )
        CommonDivider(modifier = Modifier.constrainAs(ref = divider) {
            start.linkTo(anchor = avatar.end, margin = padding)
            end.linkTo(anchor = parent.end)
            top.linkTo(anchor = avatar.bottom)
            width = Dimension.fillToConstraints
        }
        )
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_4, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLineListItem() {
    LineListItem("StephenPreview", onClickCallBack = {
        Utils.showToast(it)
    })
}