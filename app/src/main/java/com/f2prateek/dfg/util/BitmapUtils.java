/*
 * Copyright 2014 Prateek Srivastava (@f2prateek)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.f2prateek.dfg.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import java.io.IOException;

public class BitmapUtils {

  /**
   * Returns a mutable bitmap from a uri.
   *
   * @param uri Uri to the file
   * @return A mutable copy of the decoded {@link android.graphics.Bitmap}; null if failed.
   * @throws java.io.IOException if unable to make it mutable
   */
  public static Bitmap decodeUri(final ContentResolver resolver, final Uri uri) throws IOException {
    BitmapFactory.Options opt = new BitmapFactory.Options();
    opt.inJustDecodeBounds = false;
    opt.inMutable = true;
    return BitmapFactory.decodeStream(resolver.openInputStream(uri), null, opt);
  }

  /**
   * Returns a mutable bitmap from a resource.
   *
   * @param context Everything needs a context =(
   * @return A mutable copy of the resource
   */
  public static Bitmap decodeResource(final Context context, final String resourceName) {
    Resources resources = context.getResources();
    BitmapFactory.Options opt = new BitmapFactory.Options();
    opt.inMutable = true;
    return BitmapFactory.decodeResource(resources,
        getResourceIdentifierForDrawable(context, resourceName), opt);
  }

  /**
   * Get the identifier for a drawable resource with the given name
   *
   * @param context Everything needs a context =(
   * @param resourceName Name of the resource
   */
  public static int getResourceIdentifierForDrawable(final Context context,
      final String resourceName) {
    Resources resources = context.getResources();
    String packageName = context.getPackageName();
    return resources.getIdentifier(resourceName, "drawable", packageName);
  }

  /** https://developer.android.com/training/displaying-bitmaps/load-bitmap.html */
  private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth,
      int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

      // Calculate ratios of height and width to requested height and width
      final int heightRatio = Math.round((float) height / (float) reqHeight);
      final int widthRatio = Math.round((float) width / (float) reqWidth);

      // Choose the smallest ratio as inSampleSize value, this will guarantee
      // a final image with both dimensions larger than or equal to the
      // requested height and width.
      inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    return inSampleSize;
  }

  public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth,
      int reqHeight) {

    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(res, resId, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeResource(res, resId, options);
  }
}
