package com.wisecoders.jdbc.redis

import java.sql.DatabaseMetaData
import org.assertj.core.api.WithAssertions
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test

@Disabled
class RedisDatabaseMetaDataTest : BaseTest(), WithAssertions {
    private val metadata: DatabaseMetaData = RedisDatabaseMetaData()

    @Test
    @Throws(Exception::class)
    fun driverInformation() {
        assertThat(metadata.driverName).isEqualTo("Redis JDBC Driver")
        assertThat(metadata.driverMajorVersion).isEqualTo(0)
        assertThat(metadata.driverMinorVersion).isEqualTo(1)
    }

    //@Disabled("failing test")
    @Test
    @Throws(Exception::class)
    fun validateUnimplementedMethods() {
        val metadata: DatabaseMetaData = RedisDatabaseMetaData()

        assertNotSupported{ metadata.unwrap<Any?>(null) }
        assertNotSupported{ metadata.isWrapperFor(null) }
        assertNotSupported{ metadata.allProceduresAreCallable() }
        assertNotSupported{ metadata.allTablesAreSelectable() }
        assertNotSupported{ metadata.url }
        assertNotSupported{ metadata.userName }
        assertNotSupported{ metadata.isReadOnly }
        assertNotSupported{ metadata.nullsAreSortedHigh() }
        assertNotSupported{ metadata.nullsAreSortedLow() }
        assertNotSupported{ metadata.nullsAreSortedAtStart() }
        assertNotSupported{ metadata.nullsAreSortedAtEnd() }
        assertNotSupported{ metadata.usesLocalFiles() }
        assertNotSupported{ metadata.usesLocalFilePerTable() }
        assertNotSupported{ metadata.supportsMixedCaseIdentifiers() }
        assertNotSupported{ metadata.storesUpperCaseIdentifiers() }
        assertNotSupported{ metadata.storesLowerCaseIdentifiers() }
        assertNotSupported{ metadata.storesMixedCaseIdentifiers() }
        assertNotSupported{ metadata.supportsMixedCaseQuotedIdentifiers() }
        assertNotSupported{ metadata.storesUpperCaseQuotedIdentifiers() }
        assertNotSupported{ metadata.storesLowerCaseQuotedIdentifiers() }
        assertNotSupported{ metadata.storesMixedCaseQuotedIdentifiers() }
        assertNotSupported{ metadata.identifierQuoteString }
        assertNotSupported{ metadata.sqlKeywords }
        assertNotSupported{ metadata.numericFunctions }
        assertNotSupported{ metadata.stringFunctions }
        assertNotSupported{ metadata.systemFunctions }
        assertNotSupported{ metadata.timeDateFunctions }
        assertNotSupported{ metadata.searchStringEscape }
        assertNotSupported{ metadata.extraNameCharacters }
        assertNotSupported{ metadata.supportsAlterTableWithAddColumn() }
        assertNotSupported{ metadata.supportsAlterTableWithDropColumn() }
        assertNotSupported{ metadata.supportsColumnAliasing() }
        assertNotSupported{ metadata.nullPlusNonNullIsNull() }
        assertNotSupported{ metadata.supportsConvert() }
        assertNotSupported{ metadata.supportsConvert(0, 0) }
        assertNotSupported{ metadata.supportsTableCorrelationNames() }
        assertNotSupported{ metadata.supportsDifferentTableCorrelationNames() }
        assertNotSupported{ metadata.supportsExpressionsInOrderBy() }
        assertNotSupported{ metadata.supportsOrderByUnrelated() }
        assertNotSupported{ metadata.supportsGroupBy() }
        assertNotSupported{ metadata.supportsGroupByUnrelated() }
        assertNotSupported{ metadata.supportsGroupByBeyondSelect() }
        assertNotSupported{ metadata.supportsLikeEscapeClause() }
        assertNotSupported{ metadata.supportsMultipleResultSets() }
        assertNotSupported{ metadata.supportsMultipleTransactions() }
        assertNotSupported{ metadata.supportsNonNullableColumns() }
        assertNotSupported{ metadata.supportsMinimumSQLGrammar() }
        assertNotSupported{ metadata.supportsCoreSQLGrammar() }
        assertNotSupported{ metadata.supportsExtendedSQLGrammar() }
        assertNotSupported{ metadata.supportsANSI92EntryLevelSQL() }
        assertNotSupported{ metadata.supportsANSI92IntermediateSQL() }
        assertNotSupported{ metadata.supportsANSI92FullSQL() }
        assertNotSupported{ metadata.supportsIntegrityEnhancementFacility() }
        assertNotSupported{ metadata.supportsOuterJoins() }
        assertNotSupported{ metadata.supportsFullOuterJoins() }
        assertNotSupported{ metadata.supportsLimitedOuterJoins() }
        assertNotSupported{ metadata.schemaTerm }
        assertNotSupported{ metadata.procedureTerm }
        assertNotSupported{ metadata.catalogTerm }
        assertNotSupported{ metadata.isCatalogAtStart }
        assertNotSupported{ metadata.catalogSeparator }
        assertNotSupported{ metadata.supportsSchemasInDataManipulation() }
        assertNotSupported{ metadata.supportsSchemasInProcedureCalls() }
        assertNotSupported{ metadata.supportsSchemasInTableDefinitions() }
        assertNotSupported{ metadata.supportsSchemasInIndexDefinitions() }
        assertNotSupported{ metadata.supportsSchemasInPrivilegeDefinitions() }
        assertNotSupported{ metadata.supportsCatalogsInDataManipulation() }
        assertNotSupported{ metadata.supportsCatalogsInProcedureCalls() }
        assertNotSupported{ metadata.supportsCatalogsInTableDefinitions() }
        assertNotSupported{ metadata.supportsCatalogsInIndexDefinitions() }
        assertNotSupported{ metadata.supportsCatalogsInPrivilegeDefinitions() }
        assertNotSupported{ metadata.supportsPositionedDelete() }
        assertNotSupported{ metadata.supportsPositionedUpdate() }
        assertNotSupported{ metadata.supportsSelectForUpdate() }
        assertNotSupported{ metadata.supportsStoredProcedures() }
        assertNotSupported{ metadata.supportsSubqueriesInComparisons() }
        assertNotSupported{ metadata.supportsSubqueriesInExists() }
        assertNotSupported{ metadata.supportsSubqueriesInIns() }
        assertNotSupported{ metadata.supportsSubqueriesInQuantifieds() }
        assertNotSupported{ metadata.supportsCorrelatedSubqueries() }
        assertNotSupported{ metadata.supportsUnion() }
        assertNotSupported{ metadata.supportsUnionAll() }
        assertNotSupported{ metadata.supportsOpenCursorsAcrossCommit() }
        assertNotSupported{ metadata.supportsOpenCursorsAcrossRollback() }
        assertNotSupported{ metadata.supportsOpenStatementsAcrossCommit() }
        assertNotSupported{ metadata.supportsOpenStatementsAcrossRollback() }
        assertNotSupported{ metadata.maxBinaryLiteralLength }
        assertNotSupported{ metadata.maxCharLiteralLength }
        assertNotSupported{ metadata.maxColumnNameLength }
        assertNotSupported{ metadata.maxColumnsInGroupBy }
        assertNotSupported{ metadata.maxColumnsInIndex }
        assertNotSupported{ metadata.maxColumnsInOrderBy }
        assertNotSupported{ metadata.maxColumnsInSelect }
        assertNotSupported{ metadata.maxColumnsInTable }
        assertNotSupported{ metadata.maxConnections }
        assertNotSupported{ metadata.maxCursorNameLength }
        assertNotSupported{ metadata.maxIndexLength }
        assertNotSupported{ metadata.maxSchemaNameLength }
        assertNotSupported{ metadata.maxProcedureNameLength }
        assertNotSupported{ metadata.maxCatalogNameLength }
        assertNotSupported{ metadata.maxRowSize }
        assertNotSupported{ metadata.doesMaxRowSizeIncludeBlobs() }
        assertNotSupported{ metadata.maxStatementLength }
        assertNotSupported{ metadata.maxStatements }
        assertNotSupported{ metadata.maxTableNameLength }
        assertNotSupported{ metadata.maxTablesInSelect }
        assertNotSupported{ metadata.maxUserNameLength }
        assertNotSupported{ metadata.defaultTransactionIsolation }
        assertNotSupported{ metadata.supportsTransactions() }
        assertNotSupported{ metadata.supportsTransactionIsolationLevel(0) }
        assertNotSupported{ metadata.supportsDataDefinitionAndDataManipulationTransactions() }
        assertNotSupported{ metadata.supportsDataManipulationTransactionsOnly() }
        assertNotSupported{ metadata.dataDefinitionCausesTransactionCommit() }
        assertNotSupported{ metadata.dataDefinitionIgnoredInTransactions() }
        assertNotSupported{ metadata.getProcedures("", "", "") }
        assertNotSupported{ metadata.getProcedureColumns("", "", "", "") }
        assertNotSupported{ metadata.getTables("", "", "", null) }
        assertNotSupported{ metadata.tableTypes }
        assertNotSupported{ metadata.getColumns("", "", "", "") }
        assertNotSupported{ metadata.getColumnPrivileges("", "", "", "") }
        assertNotSupported{ metadata.getTablePrivileges("", "", "") }
        assertNotSupported{ metadata.getBestRowIdentifier("", "", "", 0, true) }
        assertNotSupported{ metadata.getVersionColumns("", "", "") }
        assertNotSupported{ metadata.getPrimaryKeys("", "", "") }
        assertNotSupported{ metadata.getImportedKeys("", "", "") }
        assertNotSupported{ metadata.getExportedKeys("", "", "") }
        assertNotSupported{ metadata.getCrossReference("", "", "", "", "", "") }
        assertNotSupported{ metadata.typeInfo }
        assertNotSupported{ metadata.getIndexInfo("", "", "", true, true) }
        assertNotSupported{ metadata.supportsResultSetType(0) }
        assertNotSupported{ metadata.supportsResultSetConcurrency(0, 0) }
        assertNotSupported{ metadata.ownUpdatesAreVisible(0) }
        assertNotSupported{ metadata.ownDeletesAreVisible(0) }
        assertNotSupported{ metadata.ownInsertsAreVisible(0) }
        assertNotSupported{ metadata.othersUpdatesAreVisible(0) }
        assertNotSupported{ metadata.othersDeletesAreVisible(0) }
        assertNotSupported{ metadata.othersInsertsAreVisible(0) }
        assertNotSupported{ metadata.updatesAreDetected(0) }
        assertNotSupported{ metadata.deletesAreDetected(0) }
        assertNotSupported{ metadata.insertsAreDetected(0) }
        assertNotSupported{ metadata.supportsBatchUpdates() }
        assertNotSupported{ metadata.getUDTs("", "", "", null) }
        assertNotSupported{ metadata.connection }
        assertNotSupported{ metadata.supportsSavepoints() }
        assertNotSupported{ metadata.supportsNamedParameters() }
        assertNotSupported{ metadata.supportsMultipleOpenResults() }
        assertNotSupported{ metadata.supportsGetGeneratedKeys() }
        assertNotSupported{ metadata.getSuperTypes("", "", "") }
        assertNotSupported{ metadata.getSuperTables("", "", "") }
        assertNotSupported{ metadata.getAttributes("", "", "", "") }
        assertNotSupported{ metadata.supportsResultSetHoldability(0) }
        assertNotSupported{ metadata.resultSetHoldability }
        assertNotSupported{ metadata.databaseMajorVersion }
        assertNotSupported{ metadata.databaseMinorVersion }
        assertNotSupported{ metadata.jdbcMajorVersion }
        assertNotSupported{ metadata.jdbcMinorVersion }
        assertNotSupported{ metadata.sqlStateType }
        assertNotSupported{ metadata.locatorsUpdateCopy() }
        assertNotSupported{ metadata.supportsStatementPooling() }
        assertNotSupported{ metadata.rowIdLifetime }
        assertNotSupported{ metadata.supportsStoredFunctionsUsingCallSyntax() }
        assertNotSupported{ metadata.autoCommitFailureClosesAllResultSets() }
        assertNotSupported{ metadata.clientInfoProperties }
        assertNotSupported{ metadata.getFunctions("", "", "") }
        assertNotSupported{ metadata.getFunctionColumns("", "", "", "") }
        assertNotSupported{ metadata.getPseudoColumns("", "", "", "") }
        assertNotSupported{ metadata.generatedKeyAlwaysReturned() }
    }
}
