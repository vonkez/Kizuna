@file:OptIn(ExperimentalMaterial3Api::class)

package tr.com.alkimkivanccivi.kizuna

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.moduleinstall.ModuleInstall
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import org.ocpsoft.prettytime.PrettyTime
import java.util.Date


val p = PrettyTime()

@Composable
fun ContactsPage(navController: NavController) {
    val ctx = LocalContext.current
    val viewModel: ContactsViewModel = hiltViewModel()
    var dropdownOpen by remember { mutableStateOf(false) }
    var qrDialogOpen by remember { mutableStateOf(false) }

    val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_QR_CODE)
        .build()
    val scanner = GmsBarcodeScanning.getClient(ctx, options)

    LaunchedEffect(true){
        viewModel.startListening()
    }

    return Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                ),
                title = {
                    Text("Kizuna")
                },
                actions = {
                          Box {
                              IconButton(onClick = {
                                  dropdownOpen = true
                              }) {
                                  Icon(
                                      imageVector = Icons.Default.MoreVert,
                                      contentDescription = "Add Contact",
                                      tint = MaterialTheme.colorScheme.onPrimary
                                  )
                              }
                              DropdownMenu(
                                  expanded = dropdownOpen,
                                  onDismissRequest = { dropdownOpen = false }
                              ) {
                                  DropdownMenuItem(
                                      leadingIcon =  {
                                          Icon(imageVector = Icons.Default.QrCode, contentDescription = null)
                                      },
                                      text = {
                                          Text(text = "Show QR Code")
                                             }, onClick = {qrDialogOpen = true}
                                  )
                                  DropdownMenuItem(
                                      leadingIcon =  {
                                          Icon(imageVector = Icons.Default.Logout, contentDescription = null)
                                      },
                                      text = {
                                          Text(text = "Sign out")
                                             },
                                      onClick = {
                                          Identity.getSignInClient(ctx).signOut()
                                          Firebase.auth.signOut()
                                          navController.navigate("login")
                                      }
                                  )
                              }
                          }

                },

                )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                val moduleInstall = ModuleInstall.getClient(ctx)
                val moduleInstallRequest = ModuleInstallRequest.newBuilder()
                    .addApi(scanner)
                    .build()
                moduleInstall.installModules(moduleInstallRequest)

                val s = scanner.startScan()
                    .addOnSuccessListener { barcode ->
                        Log.d("TAG", "barcode: ${barcode.rawValue}")
                        try {
                            val (uid, displayName)= barcode.rawValue!!.split(",",limit = 2)
                            navController.navigate("chatpage/$uid/$displayName")
                        } catch (e: Exception){
                            // TODO: show error
                        }


                    }
                    .addOnCanceledListener {
                        Log.d("TAG", "barcode: cancelled")
                    }
                    .addOnFailureListener { e ->
                        Log.e("TAG", "barcode: fail",e)
                    }

                // viewModel.getMessageHistory()
            }) {
                Icon(Icons.Default.PersonAdd, contentDescription = "Add")
            }

        }

    ) {
        if (qrDialogOpen){
            Dialog(
                onDismissRequest = { qrDialogOpen = false },
                properties = DialogProperties(usePlatformDefaultWidth = false)
            ) {
                Card(
                    modifier = Modifier
                        .size(350.dp),

                    shape = RoundedCornerShape(16.dp),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Log.d("TAG", "ContactsPage: ${Firebase.auth.currentUser!!.uid},${Firebase.auth.currentUser!!.displayName}")
                        Image(
                            painter = rememberQrBitmapPainter(
                                content = "${Firebase.auth.currentUser!!.uid},${Firebase.auth.currentUser!!.displayName}",
                                size = 300.dp,
                                padding = 1.dp
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally),

                            contentDescription = null
                        )
                    }
                }
            }
        }

        Column(modifier = Modifier.padding(it)) {
            viewModel.messageByUser.forEach {entry ->
                val displayName = viewModel.users[entry.key]!!
                Surface(onClick = { navController.navigate("chatpage/${entry.key}/$displayName")}){
                    ContactRow(name = displayName, lastMessage = entry.value.last())
                }
            }
        /*
            for (x in 1..5) {
                Surface(onClick = { navController.navigate("chatpage") }) {
                    ContactRow(
                        "Ali Veli",
                        Message("1", "2", "naber 3555 54535", 1701272451000)
                    )

                }

            }
            */

        }


    }

}

@Composable
fun ContactRow(name: String, lastMessage: Message): Unit {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Outlined.Person,
            contentDescription = "Contact",
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 9.dp)
                .border(border = BorderStroke(3.dp, MaterialTheme.colorScheme.primary), CircleShape)
                .size(50.dp)
                .padding(6.dp)
        )
        Column(Modifier.weight(1f)) {
            Text(
                text = name,
                fontSize = TextUnit(18f, TextUnitType.Sp),
                fontWeight = FontWeight.W500,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Text(
                text = lastMessage.messageContent,
                fontSize = TextUnit(16f, TextUnitType.Sp),
                fontWeight = FontWeight.W400,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }


        Text(
            text = p.format(Date(lastMessage.createdAt)),
            Modifier
                .padding(6.dp)
                .align(Alignment.Top),
            softWrap = false,
            maxLines = 1
        )
    }
}