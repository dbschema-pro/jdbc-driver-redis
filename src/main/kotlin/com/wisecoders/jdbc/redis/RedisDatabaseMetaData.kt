package com.wisecoders.jdbc.redis

import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.ResultSet
import java.sql.RowIdLifetime
import java.sql.SQLException
import java.sql.SQLFeatureNotSupportedException

class RedisDatabaseMetaData : DatabaseMetaData {
    @Throws(SQLException::class)
    override fun <T> unwrap(iface: Class<T>): T {
        throw SQLFeatureNotSupportedException("unwrap")
    }

    @Throws(SQLException::class)
    override fun isWrapperFor(iface: Class<*>?): Boolean {
        throw SQLFeatureNotSupportedException("isWrapperFor")
    }

    @Throws(SQLException::class)
    override fun allProceduresAreCallable(): Boolean {
        throw SQLFeatureNotSupportedException("allProceduresAreCallable")
    }

    @Throws(SQLException::class)
    override fun allTablesAreSelectable(): Boolean {
        throw SQLFeatureNotSupportedException("allTablesAreSelectable")
    }

    @Throws(SQLException::class)
    override fun getURL(): String {
        throw SQLFeatureNotSupportedException("getURL")
    }

    @Throws(SQLException::class)
    override fun getUserName(): String {
        throw SQLFeatureNotSupportedException("getUserName")
    }

    @Throws(SQLException::class)
    override fun isReadOnly(): Boolean {
        throw SQLFeatureNotSupportedException("isReadOnly")
    }

    @Throws(SQLException::class)
    override fun nullsAreSortedHigh(): Boolean {
        throw SQLFeatureNotSupportedException("nullsAreSortedHigh")
    }

    @Throws(SQLException::class)
    override fun nullsAreSortedLow(): Boolean {
        throw SQLFeatureNotSupportedException("nullsAreSortedLow")
    }

    @Throws(SQLException::class)
    override fun nullsAreSortedAtStart(): Boolean {
        throw SQLFeatureNotSupportedException("nullsAreSortedAtStart")
    }

    @Throws(SQLException::class)
    override fun nullsAreSortedAtEnd(): Boolean {
        throw SQLFeatureNotSupportedException("nullsAreSortedAtEnd")
    }

    @Throws(SQLException::class)
    override fun getDatabaseProductName(): String {
        return "RedisJdbcDriver"
    }

    @Throws(SQLException::class)
    override fun getDatabaseProductVersion(): String {
        return "1.0"
    }

    @Throws(SQLException::class)
    override fun getDriverName(): String {
        return "Redis JDBC Driver"
    }

    @Throws(SQLException::class)
    override fun getDriverVersion(): String {
        return "1.0"
    }

    override fun getDriverMajorVersion(): Int {
        return 0
    }

    override fun getDriverMinorVersion(): Int {
        return 1
    }

    @Throws(SQLException::class)
    override fun usesLocalFiles(): Boolean {
        throw SQLFeatureNotSupportedException("usesLocalFiles")
    }

    @Throws(SQLException::class)
    override fun usesLocalFilePerTable(): Boolean {
        throw SQLFeatureNotSupportedException("usesLocalFilePerTable")
    }

    @Throws(SQLException::class)
    override fun supportsMixedCaseIdentifiers(): Boolean {
        throw SQLFeatureNotSupportedException("supportsMixedCaseIdentifiers")
    }

    @Throws(SQLException::class)
    override fun storesUpperCaseIdentifiers(): Boolean {
        throw SQLFeatureNotSupportedException("storesUpperCaseIdentifiers")
    }

    @Throws(SQLException::class)
    override fun storesLowerCaseIdentifiers(): Boolean {
        throw SQLFeatureNotSupportedException("storesLowerCaseIdentifiers")
    }

    @Throws(SQLException::class)
    override fun storesMixedCaseIdentifiers(): Boolean {
        throw SQLFeatureNotSupportedException("storesMixedCaseIdentifiers")
    }

    @Throws(SQLException::class)
    override fun supportsMixedCaseQuotedIdentifiers(): Boolean {
        throw SQLFeatureNotSupportedException("supportsMixedCaseQuotedIdentifiers")
    }

    @Throws(SQLException::class)
    override fun storesUpperCaseQuotedIdentifiers(): Boolean {
        throw SQLFeatureNotSupportedException("storesUpperCaseQuotedIdentifiers")
    }

    @Throws(SQLException::class)
    override fun storesLowerCaseQuotedIdentifiers(): Boolean {
        throw SQLFeatureNotSupportedException("storesLowerCaseQuotedIdentifiers")
    }

    @Throws(SQLException::class)
    override fun storesMixedCaseQuotedIdentifiers(): Boolean {
        throw SQLFeatureNotSupportedException("storesMixedCaseQuotedIdentifiers")
    }

    @Throws(SQLException::class)
    override fun getIdentifierQuoteString(): String {
        throw SQLFeatureNotSupportedException("getIdentifierQuoteString")
    }

    @Throws(SQLException::class)
    override fun getSQLKeywords(): String {
        throw SQLFeatureNotSupportedException("getSQLKeywords")
    }

    @Throws(SQLException::class)
    override fun getNumericFunctions(): String {
        throw SQLFeatureNotSupportedException("getNumericFunctions")
    }

    @Throws(SQLException::class)
    override fun getStringFunctions(): String {
        throw SQLFeatureNotSupportedException("getStringFunctions")
    }

    @Throws(SQLException::class)
    override fun getSystemFunctions(): String {
        throw SQLFeatureNotSupportedException("getSystemFunctions")
    }

    @Throws(SQLException::class)
    override fun getTimeDateFunctions(): String {
        throw SQLFeatureNotSupportedException("getTimeDateFunctions")
    }

    @Throws(SQLException::class)
    override fun getSearchStringEscape(): String {
        throw SQLFeatureNotSupportedException("getSearchStringEscape")
    }

    @Throws(SQLException::class)
    override fun getExtraNameCharacters(): String {
        throw SQLFeatureNotSupportedException("getExtraNameCharacters")
    }

    @Throws(SQLException::class)
    override fun supportsAlterTableWithAddColumn(): Boolean {
        throw SQLFeatureNotSupportedException("supportsAlterTableWithAddColumn")
    }

    @Throws(SQLException::class)
    override fun supportsAlterTableWithDropColumn(): Boolean {
        throw SQLFeatureNotSupportedException("supportsAlterTableWithDropColumn")
    }

    @Throws(SQLException::class)
    override fun supportsColumnAliasing(): Boolean {
        throw SQLFeatureNotSupportedException("supportsColumnAliasing")
    }

    @Throws(SQLException::class)
    override fun nullPlusNonNullIsNull(): Boolean {
        throw SQLFeatureNotSupportedException("nullPlusNonNullIsNull")
    }

    @Throws(SQLException::class)
    override fun supportsConvert(): Boolean {
        throw SQLFeatureNotSupportedException("supportsConvert")
    }

    @Throws(SQLException::class)
    override fun supportsConvert(
        fromType: Int,
        toType: Int
    ): Boolean {
        throw SQLFeatureNotSupportedException("supportsConvert")
    }

    @Throws(SQLException::class)
    override fun supportsTableCorrelationNames(): Boolean {
        throw SQLFeatureNotSupportedException("supportsTableCorrelationNames")
    }

    @Throws(SQLException::class)
    override fun supportsDifferentTableCorrelationNames(): Boolean {
        throw SQLFeatureNotSupportedException("supportsDifferentTableCorrelationNames")
    }

    @Throws(SQLException::class)
    override fun supportsExpressionsInOrderBy(): Boolean {
        throw SQLFeatureNotSupportedException("supportsExpressionsInOrderBy")
    }

    @Throws(SQLException::class)
    override fun supportsOrderByUnrelated(): Boolean {
        throw SQLFeatureNotSupportedException("supportsOrderByUnrelated")
    }

    @Throws(SQLException::class)
    override fun supportsGroupBy(): Boolean {
        throw SQLFeatureNotSupportedException("supportsGroupBy")
    }

    @Throws(SQLException::class)
    override fun supportsGroupByUnrelated(): Boolean {
        throw SQLFeatureNotSupportedException("supportsGroupByUnrelated")
    }

    @Throws(SQLException::class)
    override fun supportsGroupByBeyondSelect(): Boolean {
        throw SQLFeatureNotSupportedException("supportsGroupByBeyondSelect")
    }

    @Throws(SQLException::class)
    override fun supportsLikeEscapeClause(): Boolean {
        throw SQLFeatureNotSupportedException("supportsLikeEscapeClause")
    }

    @Throws(SQLException::class)
    override fun supportsMultipleResultSets(): Boolean {
        throw SQLFeatureNotSupportedException("supportsMultipleResultSets")
    }

    @Throws(SQLException::class)
    override fun supportsMultipleTransactions(): Boolean {
        throw SQLFeatureNotSupportedException("supportsMultipleTransactions")
    }

    @Throws(SQLException::class)
    override fun supportsNonNullableColumns(): Boolean {
        throw SQLFeatureNotSupportedException("supportsNonNullableColumns")
    }

    @Throws(SQLException::class)
    override fun supportsMinimumSQLGrammar(): Boolean {
        throw SQLFeatureNotSupportedException("supportsMinimumSQLGrammar")
    }

    @Throws(SQLException::class)
    override fun supportsCoreSQLGrammar(): Boolean {
        throw SQLFeatureNotSupportedException("supportsCoreSQLGrammar")
    }

    @Throws(SQLException::class)
    override fun supportsExtendedSQLGrammar(): Boolean {
        throw SQLFeatureNotSupportedException("supportsExtendedSQLGrammar")
    }

    @Throws(SQLException::class)
    override fun supportsANSI92EntryLevelSQL(): Boolean {
        throw SQLFeatureNotSupportedException("supportsANSI92EntryLevelSQL")
    }

    @Throws(SQLException::class)
    override fun supportsANSI92IntermediateSQL(): Boolean {
        throw SQLFeatureNotSupportedException("supportsANSI92IntermediateSQL")
    }

    @Throws(SQLException::class)
    override fun supportsANSI92FullSQL(): Boolean {
        throw SQLFeatureNotSupportedException("supportsANSI92FullSQL")
    }

    @Throws(SQLException::class)
    override fun supportsIntegrityEnhancementFacility(): Boolean {
        throw SQLFeatureNotSupportedException("supportsIntegrityEnhancementFacility")
    }

    @Throws(SQLException::class)
    override fun supportsOuterJoins(): Boolean {
        throw SQLFeatureNotSupportedException("supportsOuterJoins")
    }

    @Throws(SQLException::class)
    override fun supportsFullOuterJoins(): Boolean {
        throw SQLFeatureNotSupportedException("supportsFullOuterJoins")
    }

    @Throws(SQLException::class)
    override fun supportsLimitedOuterJoins(): Boolean {
        throw SQLFeatureNotSupportedException("supportsLimitedOuterJoins")
    }

    @Throws(SQLException::class)
    override fun getSchemaTerm(): String {
        throw SQLFeatureNotSupportedException("getSchemaTerm")
    }

    @Throws(SQLException::class)
    override fun getProcedureTerm(): String {
        throw SQLFeatureNotSupportedException("getProcedureTerm")
    }

    @Throws(SQLException::class)
    override fun getCatalogTerm(): String {
        throw SQLFeatureNotSupportedException("getCatalogTerm")
    }

    @Throws(SQLException::class)
    override fun isCatalogAtStart(): Boolean {
        throw SQLFeatureNotSupportedException("isCatalogAtStart")
    }

    @Throws(SQLException::class)
    override fun getCatalogSeparator(): String {
        throw SQLFeatureNotSupportedException("getCatalogSeparator")
    }

    @Throws(SQLException::class)
    override fun supportsSchemasInDataManipulation(): Boolean {
        throw SQLFeatureNotSupportedException("supportsSchemasInDataManipulation")
    }

    @Throws(SQLException::class)
    override fun supportsSchemasInProcedureCalls(): Boolean {
        throw SQLFeatureNotSupportedException("supportsSchemasInProcedureCalls")
    }

    @Throws(SQLException::class)
    override fun supportsSchemasInTableDefinitions(): Boolean {
        throw SQLFeatureNotSupportedException("supportsSchemasInTableDefinitions")
    }

    @Throws(SQLException::class)
    override fun supportsSchemasInIndexDefinitions(): Boolean {
        throw SQLFeatureNotSupportedException("supportsSchemasInIndexDefinitions")
    }

    @Throws(SQLException::class)
    override fun supportsSchemasInPrivilegeDefinitions(): Boolean {
        throw SQLFeatureNotSupportedException("supportsSchemasInPrivilegeDefinitions")
    }

    @Throws(SQLException::class)
    override fun supportsCatalogsInDataManipulation(): Boolean {
        throw SQLFeatureNotSupportedException("supportsCatalogsInDataManipulation")
    }

    @Throws(SQLException::class)
    override fun supportsCatalogsInProcedureCalls(): Boolean {
        throw SQLFeatureNotSupportedException("supportsCatalogsInProcedureCalls")
    }

    @Throws(SQLException::class)
    override fun supportsCatalogsInTableDefinitions(): Boolean {
        throw SQLFeatureNotSupportedException("supportsCatalogsInTableDefinitions")
    }

    @Throws(SQLException::class)
    override fun supportsCatalogsInIndexDefinitions(): Boolean {
        throw SQLFeatureNotSupportedException("supportsCatalogsInIndexDefinitions")
    }

    @Throws(SQLException::class)
    override fun supportsCatalogsInPrivilegeDefinitions(): Boolean {
        throw SQLFeatureNotSupportedException("supportsCatalogsInPrivilegeDefinitions")
    }

    @Throws(SQLException::class)
    override fun supportsPositionedDelete(): Boolean {
        throw SQLFeatureNotSupportedException("supportsPositionedDelete")
    }

    @Throws(SQLException::class)
    override fun supportsPositionedUpdate(): Boolean {
        throw SQLFeatureNotSupportedException("supportsPositionedUpdate")
    }

    @Throws(SQLException::class)
    override fun supportsSelectForUpdate(): Boolean {
        throw SQLFeatureNotSupportedException("supportsSelectForUpdate")
    }

    @Throws(SQLException::class)
    override fun supportsStoredProcedures(): Boolean {
        throw SQLFeatureNotSupportedException("supportsStoredProcedures")
    }

    @Throws(SQLException::class)
    override fun supportsSubqueriesInComparisons(): Boolean {
        throw SQLFeatureNotSupportedException("supportsSubqueriesInComparisons")
    }

    @Throws(SQLException::class)
    override fun supportsSubqueriesInExists(): Boolean {
        throw SQLFeatureNotSupportedException("supportsSubqueriesInExists")
    }

    @Throws(SQLException::class)
    override fun supportsSubqueriesInIns(): Boolean {
        throw SQLFeatureNotSupportedException("supportsSubqueriesInIns")
    }

    @Throws(SQLException::class)
    override fun supportsSubqueriesInQuantifieds(): Boolean {
        throw SQLFeatureNotSupportedException("supportsSubqueriesInQuantifieds")
    }

    @Throws(SQLException::class)
    override fun supportsCorrelatedSubqueries(): Boolean {
        throw SQLFeatureNotSupportedException("supportsCorrelatedSubqueries")
    }

    @Throws(SQLException::class)
    override fun supportsUnion(): Boolean {
        throw SQLFeatureNotSupportedException("supportsUnion")
    }

    @Throws(SQLException::class)
    override fun supportsUnionAll(): Boolean {
        throw SQLFeatureNotSupportedException("supportsUnionAll")
    }

    @Throws(SQLException::class)
    override fun supportsOpenCursorsAcrossCommit(): Boolean {
        throw SQLFeatureNotSupportedException("supportsOpenCursorsAcrossCommit")
    }

    @Throws(SQLException::class)
    override fun supportsOpenCursorsAcrossRollback(): Boolean {
        throw SQLFeatureNotSupportedException("supportsOpenCursorsAcrossRollback")
    }

    @Throws(SQLException::class)
    override fun supportsOpenStatementsAcrossCommit(): Boolean {
        throw SQLFeatureNotSupportedException("supportsOpenStatementsAcrossCommit")
    }

    @Throws(SQLException::class)
    override fun supportsOpenStatementsAcrossRollback(): Boolean {
        throw SQLFeatureNotSupportedException("supportsOpenStatementsAcrossRollback")
    }

    @Throws(SQLException::class)
    override fun getMaxBinaryLiteralLength(): Int {
        throw SQLFeatureNotSupportedException("getMaxBinaryLiteralLength")
    }

    @Throws(SQLException::class)
    override fun getMaxCharLiteralLength(): Int {
        throw SQLFeatureNotSupportedException("getMaxCharLiteralLength")
    }

    @Throws(SQLException::class)
    override fun getMaxColumnNameLength(): Int {
        throw SQLFeatureNotSupportedException("getMaxColumnNameLength")
    }

    @Throws(SQLException::class)
    override fun getMaxColumnsInGroupBy(): Int {
        throw SQLFeatureNotSupportedException("getMaxColumnsInGroupBy")
    }

    @Throws(SQLException::class)
    override fun getMaxColumnsInIndex(): Int {
        throw SQLFeatureNotSupportedException("getMaxColumnsInIndex")
    }

    @Throws(SQLException::class)
    override fun getMaxColumnsInOrderBy(): Int {
        throw SQLFeatureNotSupportedException("getMaxColumnsInOrderBy")
    }

    @Throws(SQLException::class)
    override fun getMaxColumnsInSelect(): Int {
        throw SQLFeatureNotSupportedException("getMaxColumnsInSelect")
    }

    @Throws(SQLException::class)
    override fun getMaxColumnsInTable(): Int {
        throw SQLFeatureNotSupportedException("getMaxColumnsInTable")
    }

    @Throws(SQLException::class)
    override fun getMaxConnections(): Int {
        throw SQLFeatureNotSupportedException("getMaxConnections")
    }

    @Throws(SQLException::class)
    override fun getMaxCursorNameLength(): Int {
        throw SQLFeatureNotSupportedException("getMaxCursorNameLength")
    }

    @Throws(SQLException::class)
    override fun getMaxIndexLength(): Int {
        throw SQLFeatureNotSupportedException("getMaxIndexLength")
    }

    @Throws(SQLException::class)
    override fun getMaxSchemaNameLength(): Int {
        throw SQLFeatureNotSupportedException("getMaxSchemaNameLength")
    }

    @Throws(SQLException::class)
    override fun getMaxProcedureNameLength(): Int {
        throw SQLFeatureNotSupportedException("getMaxProcedureNameLength")
    }

    @Throws(SQLException::class)
    override fun getMaxCatalogNameLength(): Int {
        throw SQLFeatureNotSupportedException("getMaxCatalogNameLength")
    }

    @Throws(SQLException::class)
    override fun getMaxRowSize(): Int {
        throw SQLFeatureNotSupportedException("getMaxRowSize")
    }

    @Throws(SQLException::class)
    override fun doesMaxRowSizeIncludeBlobs(): Boolean {
        throw SQLFeatureNotSupportedException("doesMaxRowSizeIncludeBlobs")
    }

    @Throws(SQLException::class)
    override fun getMaxStatementLength(): Int {
        throw SQLFeatureNotSupportedException("getMaxStatementLength")
    }

    @Throws(SQLException::class)
    override fun getMaxStatements(): Int {
        throw SQLFeatureNotSupportedException("getMaxStatements")
    }

    @Throws(SQLException::class)
    override fun getMaxTableNameLength(): Int {
        throw SQLFeatureNotSupportedException("getMaxTableNameLength")
    }

    @Throws(SQLException::class)
    override fun getMaxTablesInSelect(): Int {
        throw SQLFeatureNotSupportedException("getMaxTablesInSelect")
    }

    @Throws(SQLException::class)
    override fun getMaxUserNameLength(): Int {
        throw SQLFeatureNotSupportedException("getMaxUserNameLength")
    }

    @Throws(SQLException::class)
    override fun getDefaultTransactionIsolation(): Int {
        throw SQLFeatureNotSupportedException("getDefaultTransactionIsolation")
    }

    @Throws(SQLException::class)
    override fun supportsTransactions(): Boolean {
        throw SQLFeatureNotSupportedException("supportsTransactions")
    }

    @Throws(SQLException::class)
    override fun supportsTransactionIsolationLevel(level: Int): Boolean {
        throw SQLFeatureNotSupportedException("supportsTransactionIsolationLevel")
    }

    @Throws(SQLException::class)
    override fun supportsDataDefinitionAndDataManipulationTransactions(): Boolean {
        throw SQLFeatureNotSupportedException("supportsDataDefinitionAndDataManipulationTransactions")
    }

    @Throws(SQLException::class)
    override fun supportsDataManipulationTransactionsOnly(): Boolean {
        throw SQLFeatureNotSupportedException("supportsDataManipulationTransactionsOnly")
    }

    @Throws(SQLException::class)
    override fun dataDefinitionCausesTransactionCommit(): Boolean {
        throw SQLFeatureNotSupportedException("dataDefinitionCausesTransactionCommit")
    }

    @Throws(SQLException::class)
    override fun dataDefinitionIgnoredInTransactions(): Boolean {
        throw SQLFeatureNotSupportedException("dataDefinitionIgnoredInTransactions")
    }

    @Throws(SQLException::class)
    override fun getProcedures(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getProcedures")
    }

    @Throws(SQLException::class)
    override fun getProcedureColumns(
        catalog: String,
        schemaPattern: String,
        procedureNamePattern: String,
        columnNamePattern: String
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getProcedureColumns")
    }

    @Throws(SQLException::class)
    override fun getTables(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String,
        types: Array<String>
    ): ResultSet {
        return RedisResultSet(arrayOf())
    }

    @Throws(SQLException::class)
    override fun getSchemas(): ResultSet {
        return RedisResultSet(arrayOf())
    }

    @Throws(SQLException::class)
    override fun getCatalogs(): ResultSet {
        return RedisResultSet(arrayOf())
    }

    @Throws(SQLException::class)
    override fun getTableTypes(): ResultSet {
        throw SQLFeatureNotSupportedException("getTableTypes")
    }

    @Throws(SQLException::class)
    override fun getColumns(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String,
        columnNamePattern: String
    ): ResultSet {
        return RedisResultSet(arrayOf())
    }

    @Throws(SQLException::class)
    override fun getColumnPrivileges(
        catalog: String,
        schema: String,
        table: String,
        columnNamePattern: String
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getColumnPrivileges")
    }

    @Throws(SQLException::class)
    override fun getTablePrivileges(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getTablePrivileges")
    }

    @Throws(SQLException::class)
    override fun getBestRowIdentifier(
        catalog: String,
        schema: String,
        table: String,
        scope: Int,
        nullable: Boolean
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getBestRowIdentifier")
    }

    @Throws(SQLException::class)
    override fun getVersionColumns(
        catalog: String,
        schema: String,
        table: String
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getVersionColumns")
    }

    @Throws(SQLException::class)
    override fun getPrimaryKeys(
        catalog: String,
        schema: String,
        table: String
    ): ResultSet {
        return RedisResultSet(arrayOf())
    }

    @Throws(SQLException::class)
    override fun getImportedKeys(
        catalog: String,
        schema: String,
        table: String
    ): ResultSet {
        return RedisResultSet(arrayOf())
    }

    @Throws(SQLException::class)
    override fun getExportedKeys(
        catalog: String,
        schema: String,
        table: String
    ): ResultSet {
        return RedisResultSet(arrayOf())
    }

    @Throws(SQLException::class)
    override fun getCrossReference(
        parentCatalog: String,
        parentSchema: String,
        parentTable: String,
        foreignCatalog: String,
        foreignSchema: String,
        foreignTable: String
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getCrossReference")
    }

    @Throws(SQLException::class)
    override fun getTypeInfo(): ResultSet {
        throw SQLFeatureNotSupportedException("getTypeInfo")
    }

    @Throws(SQLException::class)
    override fun getIndexInfo(
        catalog: String,
        schema: String,
        table: String,
        unique: Boolean,
        approximate: Boolean
    ): ResultSet {
        return RedisResultSet(arrayOf())
    }

    @Throws(SQLException::class)
    override fun supportsResultSetType(type: Int): Boolean {
        throw SQLFeatureNotSupportedException("supportsResultSetType")
    }

    @Throws(SQLException::class)
    override fun supportsResultSetConcurrency(
        type: Int,
        concurrency: Int
    ): Boolean {
        throw SQLFeatureNotSupportedException("supportsResultSetConcurrency")
    }

    @Throws(SQLException::class)
    override fun ownUpdatesAreVisible(type: Int): Boolean {
        throw SQLFeatureNotSupportedException("ownUpdatesAreVisible")
    }

    @Throws(SQLException::class)
    override fun ownDeletesAreVisible(type: Int): Boolean {
        throw SQLFeatureNotSupportedException("ownDeletesAreVisible")
    }

    @Throws(SQLException::class)
    override fun ownInsertsAreVisible(type: Int): Boolean {
        throw SQLFeatureNotSupportedException("ownInsertsAreVisible")
    }

    @Throws(SQLException::class)
    override fun othersUpdatesAreVisible(type: Int): Boolean {
        throw SQLFeatureNotSupportedException("othersUpdatesAreVisible")
    }

    @Throws(SQLException::class)
    override fun othersDeletesAreVisible(type: Int): Boolean {
        throw SQLFeatureNotSupportedException("othersDeletesAreVisible")
    }

    @Throws(SQLException::class)
    override fun othersInsertsAreVisible(type: Int): Boolean {
        throw SQLFeatureNotSupportedException("othersInsertsAreVisible")
    }

    @Throws(SQLException::class)
    override fun updatesAreDetected(type: Int): Boolean {
        throw SQLFeatureNotSupportedException("updatesAreDetected")
    }

    @Throws(SQLException::class)
    override fun deletesAreDetected(type: Int): Boolean {
        throw SQLFeatureNotSupportedException("deletesAreDetected")
    }

    @Throws(SQLException::class)
    override fun insertsAreDetected(type: Int): Boolean {
        throw SQLFeatureNotSupportedException("insertsAreDetected")
    }

    @Throws(SQLException::class)
    override fun supportsBatchUpdates(): Boolean {
        throw SQLFeatureNotSupportedException("supportsBatchUpdates")
    }

    @Throws(SQLException::class)
    override fun getUDTs(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String,
        types: IntArray
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getUDTs")
    }

    @Throws(SQLException::class)
    override fun getConnection(): Connection {
        throw SQLFeatureNotSupportedException("getConnection")
    }

    @Throws(SQLException::class)
    override fun supportsSavepoints(): Boolean {
        throw SQLFeatureNotSupportedException("supportsSavepoints")
    }

    @Throws(SQLException::class)
    override fun supportsNamedParameters(): Boolean {
        throw SQLFeatureNotSupportedException("supportsNamedParameters")
    }

    @Throws(SQLException::class)
    override fun supportsMultipleOpenResults(): Boolean {
        throw SQLFeatureNotSupportedException("supportsMultipleOpenResults")
    }

    @Throws(SQLException::class)
    override fun supportsGetGeneratedKeys(): Boolean {
        throw SQLFeatureNotSupportedException("supportsGetGeneratedKeys")
    }

    @Throws(SQLException::class)
    override fun getSuperTypes(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getSuperTypes")
    }

    @Throws(SQLException::class)
    override fun getSuperTables(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getSuperTables")
    }

    @Throws(SQLException::class)
    override fun getAttributes(
        catalog: String,
        schemaPattern: String,
        typeNamePattern: String,
        attributeNamePattern: String
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getAttributes")
    }

    @Throws(SQLException::class)
    override fun supportsResultSetHoldability(holdability: Int): Boolean {
        throw SQLFeatureNotSupportedException("supportsResultSetHoldability")
    }

    @Throws(SQLException::class)
    override fun getResultSetHoldability(): Int {
        throw SQLFeatureNotSupportedException("getResultSetHoldability")
    }

    @Throws(SQLException::class)
    override fun getDatabaseMajorVersion(): Int {
        throw SQLFeatureNotSupportedException("getDatabaseMajorVersion")
    }

    @Throws(SQLException::class)
    override fun getDatabaseMinorVersion(): Int {
        throw SQLFeatureNotSupportedException("getDatabaseMinorVersion")
    }

    @Throws(SQLException::class)
    override fun getJDBCMajorVersion(): Int {
        throw SQLFeatureNotSupportedException("getJDBCMajorVersion")
    }

    @Throws(SQLException::class)
    override fun getJDBCMinorVersion(): Int {
        throw SQLFeatureNotSupportedException("getJDBCMinorVersion")
    }

    @Throws(SQLException::class)
    override fun getSQLStateType(): Int {
        throw SQLFeatureNotSupportedException("getSQLStateType")
    }

    @Throws(SQLException::class)
    override fun locatorsUpdateCopy(): Boolean {
        throw SQLFeatureNotSupportedException("locatorsUpdateCopy")
    }

    @Throws(SQLException::class)
    override fun supportsStatementPooling(): Boolean {
        throw SQLFeatureNotSupportedException("supportsStatementPooling")
    }

    @Throws(SQLException::class)
    override fun getRowIdLifetime(): RowIdLifetime {
        throw SQLFeatureNotSupportedException("getRowIdLifetime")
    }

    @Throws(SQLException::class)
    override fun getSchemas(
        catalog: String,
        schemaPattern: String
    ): ResultSet {
        return RedisResultSet(arrayOf())
    }

    @Throws(SQLException::class)
    override fun supportsStoredFunctionsUsingCallSyntax(): Boolean {
        throw SQLFeatureNotSupportedException("supportsStoredFunctionsUsingCallSyntax")
    }

    @Throws(SQLException::class)
    override fun autoCommitFailureClosesAllResultSets(): Boolean {
        throw SQLFeatureNotSupportedException("autoCommitFailureClosesAllResultSets")
    }

    @Throws(SQLException::class)
    override fun getClientInfoProperties(): ResultSet {
        throw SQLFeatureNotSupportedException("getClientInfoProperties")
    }

    @Throws(SQLException::class)
    override fun getFunctions(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getFunctions")
    }

    @Throws(SQLException::class)
    override fun getFunctionColumns(
        catalog: String,
        schemaPattern: String,
        functionNamePattern: String,
        columnNamePattern: String
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getFunctionColumns")
    }

    @Throws(SQLException::class)
    override fun getPseudoColumns(
        catalog: String,
        schemaPattern: String,
        tableNamePattern: String,
        columnNamePattern: String
    ): ResultSet {
        throw SQLFeatureNotSupportedException("getPseudoColumns")
    }

    @Throws(SQLException::class)
    override fun generatedKeyAlwaysReturned(): Boolean {
        throw SQLFeatureNotSupportedException("generatedKeyAlwaysReturned")
    }
}
