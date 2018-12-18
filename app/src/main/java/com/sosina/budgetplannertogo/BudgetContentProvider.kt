package com.sosina.budgetplannertogo

import android.content.*
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.provider.ContactsContract.DisplayNameSources.NICKNAME
import android.text.TextUtils
import android.util.Log


class BudgetContentProvider: ContentProvider()  {
    private val TAG = "Sosina Haile 147951180"
    // fields for the mDatabase
    companion object {
        val ID: String = "id"
        val PROVIDER_NAME: String = "com.sosina.budgetplannertogo.BudgetContentProvider"
        val MONTH: String = "month"
        val INCOME: String = "income"
        val HOUSING: String = "housing"
        val GROCERIES: String = "groceries"
        val PERSONAL: String = "personal"
        val INSURANCE: String = "insurance"
        val TRANSPORT: String = "transport"
        val CHILDREN: String = "children"
       // val ENTERTAINMENT: String = "entertainment"

        val CONTENT_URI: Uri = Uri.parse("content://$PROVIDER_NAME/budgets")
    }

    // integer values used in content URI
    val BUDGET: Int = 1
    val BUDGET_ID: Int = 2

    // projection map for a query
    private val mNicknameMap = HashMap<String, String>()

    // maps content URI "patterns" to the integer values that were set above
    var mUriMatcher: UriMatcher? = null

    // mDatabase declarations
    private var mDatabase: SQLiteDatabase? = null
    val DATABASE_NAME = "budgetplanner"
    val TABLE_NAME = "budgets"
    val DATABASE_VERSION = 1
    val CREATE_TABLE = " CREATE TABLE $TABLE_NAME " +
            "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            " month TEXT NOT NULL, "+
            " income DOUBLE, " +
            " housing DOUBLE, " +
            " groceries DOUBLE, " +
            " personal DOUBLE, " +
            " insurance DOUBLE, " +
            " transport DOUBLE, " +
            " children DOUBLE );"


    init {
        mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        mUriMatcher?.addURI(PROVIDER_NAME, "budgets", BUDGET)
        mUriMatcher?.addURI(PROVIDER_NAME, "budgets/#", BUDGET_ID)
    }

    override fun onCreate(): Boolean {
        Log.i(TAG,"content provider")
        var context = context
        var mDbHelper = DBHelper(context)

        // permissions to be writable
        mDatabase = mDbHelper.writableDatabase
        return mDatabase != null
    }

    override fun query(uri: Uri?,
                       projection: Array<out String>?,
                       selection: String?,
                       selectionArgs: Array<out String>?,
                       sortOrder: String?): Cursor {
        var queryBuilder = SQLiteQueryBuilder()
        // the TABLE_NAME to query on
        queryBuilder.tables = TABLE_NAME
        when (mUriMatcher?.match(uri)) {
            BUDGET -> queryBuilder.setProjectionMap(mNicknameMap)
            BUDGET_ID -> queryBuilder.appendWhere(ID + " =" + uri?.getLastPathSegment())
            else -> throw IllegalArgumentException("Unknown URI " + uri)
        }
        var sort_order = MONTH


        if (!TextUtils.isEmpty(sortOrder)) {
            // No sorting-> sort on names by default
            sort_order = sortOrder.toString()
        }
        val cursor = queryBuilder.query(mDatabase,
                projection, selection, selectionArgs,
                null, null, sort_order)
        /**
         * register to watch a content URI for changes
         */
        cursor.setNotificationUri(context!!.contentResolver, uri)
        return cursor
    }

    override fun insert(uri: Uri?, values: ContentValues?): Uri {

        val row = mDatabase?.insert(TABLE_NAME, "", values)
        // If record is added successfully
        if (row != null) {
            if (row > 0) {
                var newUri = ContentUris.withAppendedId(CONTENT_URI, row)
                context.getContentResolver().notifyChange(newUri, null)
                return newUri
            }
        }
        throw SQLException("Fail to add a new record into" + uri)
    }

    override fun update(uri: Uri?,
                        values: ContentValues?,
                        selection: String?,
                        selectionArgs: Array<out String>?): Int {
        var count = 0
        when (mUriMatcher?.match(uri)) {
            BUDGET -> count = mDatabase?.update(TABLE_NAME, values, selection, selectionArgs)!!
            BUDGET_ID -> {
                var whereClause = ""
                if (!TextUtils.isEmpty(selection)) {
                    whereClause = " AND($selection)"
                }
                count = mDatabase?.update(TABLE_NAME, values, "$ID = ${uri?.lastPathSegment} $whereClause", selectionArgs)!!
            }
            else -> throw IllegalArgumentException("Unsupported URI " + uri)
        }
        context.contentResolver.notifyChange(uri, null);
        return count;
    }


    override fun delete(uri: Uri?,
                        selection: String?,
                        selectionArgs: Array<out String>?): Int {
        var count = 0
        when (mUriMatcher?.match(uri)) {
            BUDGET -> count = mDatabase?.delete(TABLE_NAME, selection, selectionArgs)!!
            BUDGET_ID -> {
                var whereClause = ""
                if (!TextUtils.isEmpty(selection)) {
                    whereClause = " AND($selection)"
                }
                count = mDatabase?.delete(TABLE_NAME, "$ID = ${uri?.lastPathSegment} $whereClause", selectionArgs)!!
            }
            else -> throw IllegalArgumentException("Unsupported URI " + uri);
        }
        context.contentResolver.notifyChange(uri, null);
        return count;
    }

    override fun getType(uri: Uri?): String {
        when (mUriMatcher?.match(uri)) {
            BUDGET -> return "vnd.android.cursor.dir/vnd.example.nicknames"
            BUDGET_ID -> return "vnd.android.cursor.item/vnd.example.nicknames"
            else -> throw IllegalArgumentException("Unsupported URI:" + uri)
        }
    }

    private inner class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,
        null, DATABASE_VERSION) {

        override fun onCreate(db: SQLiteDatabase?) {
            db?.execSQL(CREATE_TABLE);
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db)
        }
    }

}
