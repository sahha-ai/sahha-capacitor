export interface SahhaPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
