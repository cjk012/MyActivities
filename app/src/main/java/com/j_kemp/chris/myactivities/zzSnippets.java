package com.j_kemp.chris.myactivities;

/**
 * Random code snippets that I haven't decided if I am going to use.
 * Created by Christopher Kemp on 18/10/17.
 */

public class zzSnippets {


    // Location("-26.717899,153.062490") is J Block @ USC Sippy Downs

/*        // Initialise user.
        User userDefault = new User();
        userDefault.setName(R.string.dummy_user_name);
        userDefault.setEmail(R.string.dummy_user_email);
        userDefault.setGender(R.string.dummy_user_gender);
        userDefault.setComment(R.string.dummy_user_comment);*/


/*    private static final String[] LOCATION_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;*/

/*    @Override

                if (hasLocationPermission()) {
                    getLocation();
                } else {
                    requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
                }

        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(@Nullable Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .build();

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()){
                    getLocation();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void getLocation() {
        LocationRequest req = LocationRequest.create();
        req.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        req.setNumUpdates(1);
        req.setInterval(0);
        LocationServices.FusedLocationApi.requestLocationUpdates(mClient, req, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.i(TAG, "Got a fix: " + location);
                currentPlace = location;
            }
        });
    }

    private boolean hasLocationPermission(){
        int result = ContextCompat.checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }*/

//<string name="task_date_button_text">Date: %1%s %2%s %3%s</string>

    /*
    <string name="task_type_work">Work</string>
    <string name="task_type_study">Study</string>
    <string name="task_type_Leisure">Leisure</string>
    <string name="task_type_sport">Sport</string>
     */
}

