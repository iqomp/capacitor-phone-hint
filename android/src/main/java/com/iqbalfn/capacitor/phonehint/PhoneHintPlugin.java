package com.iqbalfn.capacitor.phonehint;

import android.content.IntentSender;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.google.android.gms.auth.api.identity.GetPhoneNumberHintIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;

@CapacitorPlugin(name = "PhoneHint")
public class PhoneHintPlugin extends Plugin {
    ActivityResultLauncher<IntentSenderRequest> launcher;
    PluginCall call;


    @Override
    public void load() {
        launcher = bridge.registerForActivityResult(
                new ActivityResultContracts.StartIntentSenderForResult(),
                result -> {
                    try {
                        String phone = Identity.getSignInClient(getActivity())
                                .getPhoneNumberFromIntent(result.getData());
                        JSObject ret = new JSObject();
                        ret.put("phone", phone);
                        call.resolve(ret);
                    } catch (Exception e) {
                        call.reject(e.getLocalizedMessage());
                    }
                }
        );
    }

    @PluginMethod
    public void requestHint(PluginCall call) {
        this.call = call;
        GetPhoneNumberHintIntentRequest request = GetPhoneNumberHintIntentRequest.builder().build();

        Identity.getSignInClient(getActivity())
                .getPhoneNumberHintIntent(request)
                .addOnFailureListener(e -> {
                    call.reject(e.getLocalizedMessage());
                })
                .addOnSuccessListener(result -> {
                    IntentSender intent = result.getIntentSender();
                    IntentSenderRequest.Builder builder = new IntentSenderRequest.Builder(intent);
                    launcher.launch(builder.build());
                });
    }
}
