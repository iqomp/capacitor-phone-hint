export interface PhoneNumber {
    /**
     * Picket phone number
     * @since 1.0.0
     */
    phone: string
}

export interface PhoneHintPlugin {
    /**
     * Request the phone number dialog picker
     * @since 1.0.0
     */
    requestHint(): Promise<PhoneNumber>;
}
