package mx.tec.nativeexploration

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import mx.tec.nativeexploration.ui.theme.NativeExplorationTheme

// things to import in order to use state variables
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue


// jetpack
// https://developer.android.com/jetpack

// a suite of libraries with diverse functionality
// separate from the API therefore
// can be updated even if OS is not the latest
// JETPACK - ANDROIDX

// jetpack compose
// https://developer.android.com/compose

// UI specific jetpack package
// modern UI toolkit - uses aggregation / modular paradigm



// even if you are using compose you still are required to have a class
// and your compse based UI class must extend activity
class ComposeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NativeExplorationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ListExample()
                }
            }
        }
    }

    // IMPORTANT
    // SCOPE - composables are functions and can be defined within the scope of a class
    // or outside

    // within the class they have access to attributes / behaviors of the class itself
    // outside they don't
    @Composable
    fun TheButton() {
        // composables can have only arguments
        // OR work as containers
        Button(
            onClick = {
                // whatever logic works on views activities will also work here
                // the context type:
                // - an object that can interact with native behavior in the OS
                // - an activity is also a context object
                Toast.makeText(this, "TOAST FROM A BUTTON", Toast.LENGTH_SHORT).show()
            }
        ) { Text(text = "SAY HI") }
    }

    // an example of a composable that contains more
    @Composable
    fun Example1(activity: Activity) {
        // things to do here
        // something that contains our elements
        // to add several composables into a single one we need a container
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "THIS IS A TEXT")
            Text(text = "THIS IS ANOTHER TEXT")
            Image(
                painter = painterResource(R.drawable.puppy),
                contentDescription = "a cute puppy",
            )
            HiButton(activity)
        }
    }

    // INPUT TEST / STATE VARIABLES
    @Composable
    fun InputTest(activity: Activity? = null)
    {
        // state variables - internal variables of a composable
        // that can trigger an event if changed

        // event - something that happens that triggers a recompose
        // recompose - redraw
        var name by remember { mutableStateOf("doggy") }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Text("the doggy's name is: $name")
            TextField(
                value = name,
                onValueChange = {
                    // "it" is the name of the argument received
                    // when the name is not specified
                    name = it
                }
            )
            Button(
                onClick = {
                    Toast.makeText(activity, "HI $name", Toast.LENGTH_SHORT).show()
                }
            )
            {
                Text( text = "SAY HI TO THE DOGGY!")
            }
        }
    }

    // let's talk about lists
    @Composable
    fun ListExample() {
        // 2 types of lists with compose
        // "regular" - we already know size, we load it in batch
        // "lazy" - elements are shown while loaded

        // the regular one we already know
        // Column - contained elements will be arranged vertically
        // when an update occurs the column wil lbe recomposed (redrawn)
        /*
        Column {
            ListRow(R.drawable.puppy, text= "puppy1")
            ListRow(R.drawable.puppy, text= "puppy2")
            ListRow(R.drawable.puppy, text= "puppy3")
            ListRow(R.drawable.puppy, text= "puppy4")
            ListRow(R.drawable.puppy, text= "puppy5")
        }
        */

        // lazy column
        LazyColumn {
            // main difference - we will be using blocks called items

            // can be done individually
            item {
                ListRow(R.drawable.puppy, "a puppy")
            }

            // ... or by batch
            items(count = 3) { i ->
                ListRow(R.drawable.puppy, "puppy $i")
            }

        }
    }

    @Composable
    fun ListRow(id: Int, text : String) {
        // Row - contained elements will be arranged horizontally
        Row {
            Image(
                painter = painterResource(id),
                contentDescription = "a cute puppy"
            )
            Text(text)
        }
    }
}

// if you declare a composable outside of a class
// you will not have access to its attributes / behaviors

// ? - nullable
// a type that can have a null value
@Composable
fun HiButton(activity: Activity? = null) {
    Button(
        onClick = {
            Toast.makeText(activity, "HI FROM THE OUTSIDE!", Toast.LENGTH_SHORT).show()
        }
    ){
        Text("SAY HI FROM THE OUTSIDE!")
    }
}


// UIs in compose are made up of building blocks called composables
// to declare a composable we need to:
// - declare a function that returns UI
// - a @Composable annotaion
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// you can have a special composable called preview
// taht will only work on android studio
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NativeExplorationTheme {
        Greeting("Android")
    }
}