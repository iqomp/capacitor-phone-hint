import { registerPlugin } from '@capacitor/core'
import type { PhoneHintPlugin } from './definitions'

const PhoneHint = registerPlugin<PhoneHintPlugin>('PhoneHint')

export * from './definitions'
export { PhoneHint }
