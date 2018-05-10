<?php

use Illuminate\Database\Seeder;

class UsersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {

	DB::table('users')->insert([
    'username' => 'jjcanta',
    'email' => 'jjcantarino2@gmail.com',
    'password' => app('hash')->make('whatever'),
	]);
    }

}
