package com.example.escapetrapp.ui.singUp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.concrete.canarinho.validator.ValidadorTelefone
import com.example.escapetrapp.exceptions.EmailInvalidException
import com.example.escapetrapp.exceptions.PasswordInvalidException
import com.example.escapetrapp.extensions.isValidEmail
import com.example.escapetrapp.models.RequestState
import com.example.escapetrapp.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class SignUpViewModel:ViewModel(){
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()
    val signUpState = MutableLiveData<RequestState<FirebaseUser>>()


    fun signUp(newUser: User, confirmEmail: String, confirmPassword: String) {
        signUpState.value = RequestState.Loading
        if (validateFields(newUser, confirmEmail,confirmPassword)) {
            mAuth.createUserWithEmailAndPassword(
            newUser.email ?: "",
            newUser.password ?: "" )
            .addOnCompleteListener { task -> if (task.isSuccessful) {
                saveInFirestore(newUser)
            } else {
                signUpState.value = RequestState.Error(
                    Throwable(
                        task.exception?.message ?: "Não foi possível criar a conta"
                    ) )
            }
            }
        }
    }

    private fun saveInFirestore(newUser: User) {
        db.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!).set(newUser)
            .addOnSuccessListener {
                sendEmailVerification() }
            .addOnFailureListener { e -> signUpState.value = RequestState.Error(
                Throwable(e.message) )
            }
    }

    private fun sendEmailVerification() {
        mAuth.currentUser?.sendEmailVerification()
        ?.addOnCompleteListener {
            signUpState.value = RequestState.Success(mAuth.currentUser!!)
        }
    }

    private fun validateFields(newUser: User, confirmEmail: String, confirmPassword: String): Boolean {
        if (newUser.username?.isEmpty() == true) {
            signUpState.value = RequestState.Error(Throwable("Informe o nome do usuário"))
            return false
        }
        if (newUser.email?.isValidEmail() == false) {
            signUpState.value = RequestState.Error(EmailInvalidException())
            return false
        }
        if (newUser.phone?.isEmpty() == true) {
            signUpState.value = RequestState.Error(Throwable("Informe o telefone"))
            return false
        }
        if (!ValidadorTelefone.TELEFONE.ehValido(newUser.phone)) {
            signUpState.value = RequestState.Error(Throwable("Informe um telefone válido"))
            return false
        }
        if (newUser.password?.isEmpty() == true) {
            signUpState.value = RequestState.Error(PasswordInvalidException("Informe uma senha com no mínimo 6 caracteres"))
            return false
        }
        if (newUser.email?.length ?: 0 < 6) { signUpState.value =
            RequestState.Error(PasswordInvalidException("Senha deve possuir no mínimo 6 caracteres"))
            return false
        }
        if(newUser.email != confirmEmail){
            signUpState.value = RequestState.Error(Throwable("Emails preenchidos possuem diferenças"))
            return false
        }

        if(newUser.password != confirmPassword){
            signUpState.value = RequestState.Error(Throwable("Senhas preenchidas possuem diferenças"))
            return false
        }
        return true
    }
}


