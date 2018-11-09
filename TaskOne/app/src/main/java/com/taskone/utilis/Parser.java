package com.taskone.utilis;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.taskone.database.DB_PARAMS;
import com.taskone.database.DataBase;
import com.taskone.model.FavModel;
import com.taskone.model.PlaceDetailsModel;
import com.taskone.model.PlacesModel;
import com.taskone.model.ResultPagePair;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Parser {
    private static final String TAG = "Parser";
    private static final String KEY_AUTHER_NAME = "author_name";
    private static final String KEY_AUTHER__PAGE = "author_url";
    private static final String KEY_DEFAULTIMAGE_URL = "icon";
    private static final String KEY_FOLLOWGOOGLE_PLUS = "url";
    private static final String KEY_FORMATTER_ADDRESS = "formatted_address";
    private static final String KEY_GEOCODE_ADDRESS = "address_component";
    private static final String KEY_GEOCODE_LOCALITY_TYPE = "type";
    private static final String KEY_GEOCODE_LOCATION = "location";
    private static final String KEY_GEOCODE_LONG_NAME = "long_name";
    private static final String KEY_GEOCODE_RESPONCE = "GeocodeResponse";
    private static String KEY_ICON = KEY_DEFAULTIMAGE_URL;
    private static final String KEY_INTERNATION_PHONE = "international_phone_number";
    private static String KEY_LAT = "lat";
    private static String KEY_LNG = "lng";
    private static String KEY_NAME = "name";
    private static String KEY_NEXTPAGE_TOKEN = "next_page_token";
    private static String KEY_OPEN_NOW = "open_now";
    private static String KEY_PHOTOREFERENCE = "photo_reference";
    private static String KEY_PLACE_SEARCH_RESPONCE = "PlaceSearchResponse";
    private static String KEY_PRICELEVEL = "price_level";
    private static String KEY_RATING = "rating";
    private static String KEY_REFERENCE = "reference";
    private static String KEY_RESULT = "result";
    private static final String KEY_REVIEW = "review";
    private static final String KEY_REVIEW_RATING = "rating";
    private static final String KEY_REVIEW_TEXT = "text";
    private static final String KEY_REVIEW_TIME = "time";
    private static String KEY_STATUS = "status";
    private static String KEY_VICINITY = "vicinity";
    private static final String KEY_WEBSITE = "website";


    public ResultPagePair getResponceNodeList(String service_url) {
        String searchResponce = getUrlContents(service_url);
        ResultPagePair pair = new ResultPagePair();
        if (searchResponce != null) {
            try {
                Document doc = getDomElement(searchResponce);
                if (checkResultResponce(doc).equals("OK")) {
                    pair.setItem(doc.getElementsByTagName(KEY_RESULT));
                    pair.setNextPageToken(getValue((Element) doc.getElementsByTagName(KEY_PLACE_SEARCH_RESPONCE).item(0), KEY_NEXTPAGE_TOKEN));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return pair;
    }

    public String checkResultResponce(Document doc) {
        if (getValue((Element) doc.getElementsByTagName(KEY_PLACE_SEARCH_RESPONCE).item(0), KEY_STATUS).equals("OK")) {
            return "OK";
        }
        return "NOTOK";
    }

    public PlacesModel getResult(NodeList searchresult, int position, Activity activity) {
        DataBase db = new DataBase(activity);
        PlacesModel result = new PlacesModel();
        try {
            Element e1 = (Element) searchresult.item(position);
            String name = getValue(e1, KEY_NAME);
            String vicinity = getValue(e1, KEY_FORMATTER_ADDRESS);
            String lat = getValue(e1, KEY_LAT);
            String lng = getValue(e1, KEY_LNG);
            String open_now = getValue(e1, KEY_OPEN_NOW);
            String rating = getValue(e1, KEY_RATING);
            String photo_reference = getValue(e1, KEY_PHOTOREFERENCE);
            String icon = getValue(e1, KEY_ICON);
//            if (photo_reference.equals("") || photo_reference == null) {
//                result.setPhoto_reference_url(icon);
//            } else {
//                result.setPhoto_reference_url(UrlHandler.buildUrlForPhotoDetails(photo_reference, UrlHandler.appkey));
//            }
            result.setReference(getValue(e1, KEY_REFERENCE));
            result.setName(name);
            result.setVicinity(vicinity);
            result.setLat(lat);
            result.setLng(lng);
            result.setOperating_time(open_now);


            /*Cursor c = db.getAllData();
            do {
                String fvname = c.getString(c.getColumnIndex(DB_PARAMS.COLUMNS.favListColumn.name));
                Log.e(TAG, "getResult: " + fvname);
                if (name.equals(fvname)) {
                    result.setStatus(true);
                } else {
                    result.setStatus(false);
                }
            } while (c.moveToNext());*/


//            result.setRating(Float.parseFloat(rating));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Bitmap parseImage(String url) {
        try {
            Bitmap bmp = BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
            Bitmap bitmap = Bitmap.createScaledBitmap(bmp, 200, 200, true);
            bmp.recycle();
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getPhotoReference(NodeList node, int position) {
        return getValue((Element) node.item(position), KEY_PHOTOREFERENCE);
    }

    public PlaceDetailsModel getPlaceDetails(String url) {
        PlaceDetailsModel placeDetails = new PlaceDetailsModel();
        String urlcontent = getUrlContents(url);
        if (urlcontent != null) {
            Document doc = getDomElement(urlcontent);
            NodeList items = doc.getElementsByTagName(KEY_RESULT);
            int i = 0;
            while (i < items.getLength()) {
                Element e1 = (Element) items.item(i);
                String name = getValue(e1, KEY_NAME);
                String address = getValue(e1, KEY_FORMATTER_ADDRESS);
                String phone = getValue(e1, KEY_INTERNATION_PHONE);
                String website = getValue(e1, KEY_WEBSITE);
                String googlePlusPage = getValue(e1, "url");
                String lat = getValue(e1, KEY_LAT);
                String lng = getValue(e1, KEY_LNG);
                String open = getValue(e1, KEY_OPEN_NOW);
                String photoreference = getValue(e1, KEY_DEFAULTIMAGE_URL);
                float priceLevel = BitmapDescriptorFactory.HUE_RED;
                try {
                    priceLevel = Float.parseFloat(getValue(e1, KEY_PRICELEVEL));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    NodeList photoreferenceList = doc.getElementsByTagName("photo");
                    // List<ReviewPojo> reviewList = new ArrayList();
                    NodeList reviewNode = doc.getElementsByTagName(KEY_REVIEW);
                    for (int j = 0; j < reviewNode.getLength(); j++) {
                        //   ReviewPojo reviewItem = new ReviewPojo();
                        Element e2 = (Element) reviewNode.item(j);
                        String authorName = getValue(e2, KEY_AUTHER_NAME);
                        String authorPage = getValue(e2, KEY_AUTHER__PAGE);
                        String review = getValue(e2, KEY_REVIEW_TEXT);
                        String reviewTime = getValue(e2, KEY_REVIEW_TIME);
                        float reviewRating = Float.parseFloat(getValue(e2, KEY_REVIEW_RATING));
                        /*reviewItem.setAuthorname(authorName);
                        reviewItem.setAutherReview(review);
                        reviewItem.setAuthorurl(authorPage);
                        reviewItem.setAuthorrating(reviewRating);
                        reviewItem.setReviewtime(reviewTime);
                        reviewList.add(reviewItem);*/
                    }
                    placeDetails.setName(name);
                    placeDetails.setAddress(address);
                    placeDetails.setPhone(phone);
                    placeDetails.setWebsite(website);
                    placeDetails.setLat(Double.parseDouble(lat));
                    placeDetails.setLng(Double.parseDouble(lng));
                    placeDetails.setGooglePlusPage(googlePlusPage);
                    placeDetails.setPhotoReference(photoreference);
//                    placeDetails.setReview(reviewList);
                    placeDetails.setOpen(open);
                    placeDetails.setPhotoList(photoreferenceList);
                    placeDetails.setPriceLevel(priceLevel);
                    i++;
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        }
        return placeDetails;
    }


    private String getUrlContents(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(theUrl).openConnection().getInputStream()), 8);
            while (true) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                content.append(new StringBuilder(String.valueOf(line)).toString());
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public Document getDomElement(String xml) {
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml));
            return db.parse(is);
        } catch (ParserConfigurationException e) {
            Log.e("Error: ", e.getMessage());
            return null;
        } catch (SAXException e2) {
            Log.e("Error: ", e2.getMessage());
            return null;
        } catch (IOException e3) {
            Log.e("Error: ", e3.getMessage());
            return null;
        }
    }

    public final String getElementValue(Node elem) {
        if (elem != null && elem.hasChildNodes()) {
            Node child = elem.getFirstChild();
            while (child != null) {
                if (child.getNodeType() == (short) 3 || child.getNodeType() == (short) 4) {
                    return child.getNodeValue();
                }
                child = child.getNextSibling();
            }
        }
        return "";
    }

    public String getValue(Element item, String str) {
        return getElementValue(item.getElementsByTagName(str).item(0));
    }
}
