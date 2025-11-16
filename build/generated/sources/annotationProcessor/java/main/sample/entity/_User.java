package sample.entity;

/** */
@javax.annotation.processing.Generated(value = { "Doma", "2.59.0" }, date = "2025-11-15T13:59:05.445+0000")
@org.seasar.doma.EntityTypeImplementation
public final class _User extends org.seasar.doma.jdbc.entity.AbstractEntityType<sample.entity.User> {

    static {
        org.seasar.doma.internal.Artifact.validateVersion("2.59.0");
    }

    private static final _User __singleton = new _User();

    private final org.seasar.doma.jdbc.entity.NamingType __namingType = org.seasar.doma.jdbc.entity.NamingType.SNAKE_LOWER_CASE;

    private final java.util.function.Supplier<org.seasar.doma.jdbc.entity.NullEntityListener<sample.entity.User>> __listenerSupplier;

    private final boolean __immutable;

    private final String __catalogName;

    private final String __schemaName;

    private final String __tableName;

    private final boolean __isQuoteRequired;

    private final String __name;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> __idPropertyTypes;

    private final java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> __entityPropertyTypes;

    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> __entityPropertyTypeMap;

    @SuppressWarnings("unused")
    private final java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<sample.entity.User, ?>> __embeddedPropertyTypeMap;

    private _User() {
        __listenerSupplier = org.seasar.doma.internal.jdbc.entity.NullEntityListenerSuppliers.of();
        __immutable = false;
        __name = "User";
        __catalogName = "";
        __schemaName = "";
        __tableName = "";
        __isQuoteRequired = false;
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> __idList = new java.util.ArrayList<>();
        java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> __list = new java.util.ArrayList<>(9);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> __map = new java.util.LinkedHashMap<>(9);
        java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<sample.entity.User, ?>> __embeddedMap = new java.util.LinkedHashMap<>(9);
        initializeMaps(__map, __embeddedMap);
        initializeIdList(__map, __idList);
        initializeList(__map, __list);
        __idPropertyTypes = java.util.Collections.unmodifiableList(__idList);
        __entityPropertyTypes = java.util.Collections.unmodifiableList(__list);
        __entityPropertyTypeMap = java.util.Collections.unmodifiableMap(__map);
        __embeddedPropertyTypeMap = java.util.Collections.unmodifiableMap(__embeddedMap);
    }

    private void initializeMaps(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> __map, java.util.Map<String, org.seasar.doma.jdbc.entity.EmbeddedPropertyType<sample.entity.User, ?>> __embeddedMap) {
        __map.put("id", new org.seasar.doma.jdbc.entity.DefaultPropertyType<sample.entity.User, java.lang.Long, java.lang.Long>(sample.entity.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofLong(), "id", "", __namingType, true, true, false));
        __map.put("userId", new org.seasar.doma.jdbc.entity.DefaultPropertyType<sample.entity.User, java.lang.String, java.lang.String>(sample.entity.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "userId", "", __namingType, true, true, false));
        __map.put("name", new org.seasar.doma.jdbc.entity.DefaultPropertyType<sample.entity.User, java.lang.String, java.lang.String>(sample.entity.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "name", "", __namingType, true, true, false));
        __map.put("email", new org.seasar.doma.jdbc.entity.DefaultPropertyType<sample.entity.User, java.lang.String, java.lang.String>(sample.entity.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "email", "", __namingType, true, true, false));
        __map.put("password", new org.seasar.doma.jdbc.entity.DefaultPropertyType<sample.entity.User, java.lang.String, java.lang.String>(sample.entity.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofString(), "password", "", __namingType, true, true, false));
        __map.put("role", new org.seasar.doma.jdbc.entity.DefaultPropertyType<sample.entity.User, java.lang.Integer, java.lang.Integer>(sample.entity.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofPrimitiveInt(), "role", "", __namingType, true, true, false));
        __map.put("createdAt", new org.seasar.doma.jdbc.entity.DefaultPropertyType<sample.entity.User, java.time.LocalDateTime, java.time.LocalDateTime>(sample.entity.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofLocalDateTime(), "createdAt", "", __namingType, true, true, false));
        __map.put("updatedAt", new org.seasar.doma.jdbc.entity.DefaultPropertyType<sample.entity.User, java.time.LocalDateTime, java.time.LocalDateTime>(sample.entity.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofLocalDateTime(), "updatedAt", "", __namingType, true, true, false));
        __map.put("deletedAt", new org.seasar.doma.jdbc.entity.DefaultPropertyType<sample.entity.User, java.time.LocalDateTime, java.time.LocalDateTime>(sample.entity.User.class, org.seasar.doma.internal.jdbc.scalar.BasicScalarSuppliers.ofLocalDateTime(), "deletedAt", "", __namingType, true, true, false));
    }

    private void initializeIdList(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> __map, java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> __idList) {
    }

    private void initializeList(java.util.Map<String, org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> __map, java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> __list) {
        __list.addAll(__map.values());
    }

    @Override
    public org.seasar.doma.jdbc.entity.NamingType getNamingType() {
        return __namingType;
    }

    @Override
    public boolean isImmutable() {
        return __immutable;
    }

    @Override
    public String getName() {
        return __name;
    }

    @Override
    public String getCatalogName() {
        return __catalogName;
    }

    @Override
    public String getSchemaName() {
        return __schemaName;
    }

    @Override
    @Deprecated
    public String getTableName() {
        return getTableName(org.seasar.doma.internal.jdbc.entity.TableNames.namingFunction);
    }

    @Override
    public String getTableName(java.util.function.BiFunction<org.seasar.doma.jdbc.entity.NamingType, String, String> namingFunction) {
        if (__tableName.isEmpty()) {
            return namingFunction.apply(__namingType, __name);
        }
        return __tableName;
    }

    @Override
    public boolean isQuoteRequired() {
        return __isQuoteRequired;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preInsert(sample.entity.User entity, org.seasar.doma.jdbc.entity.PreInsertContext<sample.entity.User> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preUpdate(sample.entity.User entity, org.seasar.doma.jdbc.entity.PreUpdateContext<sample.entity.User> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void preDelete(sample.entity.User entity, org.seasar.doma.jdbc.entity.PreDeleteContext<sample.entity.User> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.preDelete(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postInsert(sample.entity.User entity, org.seasar.doma.jdbc.entity.PostInsertContext<sample.entity.User> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postInsert(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postUpdate(sample.entity.User entity, org.seasar.doma.jdbc.entity.PostUpdateContext<sample.entity.User> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postUpdate(entity, context);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void postDelete(sample.entity.User entity, org.seasar.doma.jdbc.entity.PostDeleteContext<sample.entity.User> context) {
        Class __listenerClass = org.seasar.doma.jdbc.entity.NullEntityListener.class;
        org.seasar.doma.jdbc.entity.NullEntityListener __listener = context.getConfig().getEntityListenerProvider().get(__listenerClass, __listenerSupplier);
        __listener.postDelete(entity, context);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> getEntityPropertyTypes() {
        return __entityPropertyTypes;
    }

    @Override
    public org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?> getEntityPropertyType(String __name) {
        return __entityPropertyTypeMap.get(__name);
    }

    @Override
    public java.util.List<org.seasar.doma.jdbc.entity.EntityPropertyType<sample.entity.User, ?>> getIdPropertyTypes() {
        return __idPropertyTypes;
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<sample.entity.User, ?, ?> getGeneratedIdPropertyType() {
        return (org.seasar.doma.jdbc.entity.GeneratedIdPropertyType<sample.entity.User, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.VersionPropertyType<sample.entity.User, ?, ?> getVersionPropertyType() {
        return (org.seasar.doma.jdbc.entity.VersionPropertyType<sample.entity.User, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @SuppressWarnings("unchecked")
    @Override
    public org.seasar.doma.jdbc.entity.TenantIdPropertyType<sample.entity.User, ?, ?> getTenantIdPropertyType() {
        return (org.seasar.doma.jdbc.entity.TenantIdPropertyType<sample.entity.User, ?, ?>)__entityPropertyTypeMap.get("null");
    }

    @Override
    public sample.entity.User newEntity(java.util.Map<String, org.seasar.doma.jdbc.entity.Property<sample.entity.User, ?>> __args) {
        sample.entity.User entity = new sample.entity.User();
        if (__args.get("id") != null) __args.get("id").save(entity);
        if (__args.get("userId") != null) __args.get("userId").save(entity);
        if (__args.get("name") != null) __args.get("name").save(entity);
        if (__args.get("email") != null) __args.get("email").save(entity);
        if (__args.get("password") != null) __args.get("password").save(entity);
        if (__args.get("role") != null) __args.get("role").save(entity);
        if (__args.get("createdAt") != null) __args.get("createdAt").save(entity);
        if (__args.get("updatedAt") != null) __args.get("updatedAt").save(entity);
        if (__args.get("deletedAt") != null) __args.get("deletedAt").save(entity);
        return entity;
    }

    @Override
    public Class<sample.entity.User> getEntityClass() {
        return sample.entity.User.class;
    }

    @Override
    public sample.entity.User getOriginalStates(sample.entity.User __entity) {
        return null;
    }

    @Override
    public void saveCurrentStates(sample.entity.User __entity) {
    }

    /**
     * @return the singleton
     */
    public static _User getSingletonInternal() {
        return __singleton;
    }

    /**
     * @return the new instance
     */
    public static _User newInstance() {
        return new _User();
    }

}
